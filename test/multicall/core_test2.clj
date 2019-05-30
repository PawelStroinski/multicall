(ns multicall.core-test2
  (:require [midje.sweet :refer :all]
            [multicall.core :refer [defmulticall defcall]]
            [multicall.core-test :as test]))

(defmulticall foo :v)
(defcall foo one :hit [m] (assoc m :foo 1))

(fact (foo {:v :hit}) => [{:v :hit :foo 1}])

(defcall test/bar :hit [m] (assoc m :bar 5))
(defcall test/bar one :hit [m] (assoc m :bar 6))

(fact (set (test/bar {:v :hit}))
  => #{{:v :hit :bar 2} {:v :hit :bar 4} {:v :hit :bar 5} {:v :hit :bar 6}})

(facts "catch-all in a namespace"
  (defmulticall baz :v)
  (defcall baz :hit/one [m] (assoc m :baz 1))
  (defcall baz :hit/* [m] (assoc m :baz 0))
  (defcall baz :hit/two [m] (assoc m :baz 2))
  (defcall baz "non-keyword" [m] (assoc m :baz 3))
  
  (set (baz {:v :hit/one})) => #{{:v :hit/one :baz 0} {:v :hit/one :baz 1}}
  (set (baz {:v :hit/two})) => #{{:v :hit/two :baz 0} {:v :hit/two :baz 2}}
  (baz {:v :hit/three}) => [{:v :hit/three :baz 0}]
  (baz {:v :miss/one}) => ()
  (baz {:v "non-keyword"}) => [{:v "non-keyword" :baz 3}])
