(defproject multicall "0.1.0-alpha1"
  :description "Like multimethods, but can register multiple calls with the same dispatch value."
  :url "https://github.com/PawelStroinski/multicall"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:dev {:dependencies [[midje "1.9.1"]]}})