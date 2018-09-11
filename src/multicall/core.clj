(ns multicall.core)

(defonce registry (atom nil))

(defn multicall*
  [ns name dispatch-fn & args]
  (let [dispatch-val (apply dispatch-fn args)]
    (for [f (vals (get @registry [ns name dispatch-val]))]
      (apply f args))))

(defmacro defmulticall
  "Like defmulti, but created multicall can have multiple calls with the same
  dispatch value registered."
  [name dispatch-fn]
  `(defn ~name [& ~'args] (apply multicall* ~*ns* '~name ~dispatch-fn ~'args)))

(defn defcall*
  [multifn-var name dispatch-val f]
  (let [id (conj ((juxt :ns :name) (meta multifn-var)) dispatch-val)]
    (swap! registry assoc-in [id [*ns* name]] f)))

(defmacro defcall
  "Like defmethod, but can register multiple calls with the same dispatch value.
  However, a call WILL override a previously registered call if all of the below
  are true:

    - the call names (name? argument) are the same or both omitted;
    - the dispatch values are the same;
    - both calls are registered from the same namespace."
  [multifn name? dispatch-val & fn-tail]
  (let [name (if (symbol? name?) name? 'multicall.core/default)
        dispatch-val' (if (symbol? name?) dispatch-val name?)
        fn-tail' (if (symbol? name?) fn-tail (cons dispatch-val fn-tail))]
    `(defcall* (var ~multifn) '~name ~dispatch-val' (fn ~name ~@fn-tail'))))
