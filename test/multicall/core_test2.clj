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
