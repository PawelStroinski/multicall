(ns multicall.core-test
  (:require [midje.sweet :refer :all]
            [multicall.core :refer [defmulticall defcall]]))

(defmulticall foo :v)
(defcall foo one :hit [m] (assoc m :foo 1))
(defcall foo two :hit [m] (assoc m :foo 2))
(defcall foo three :hit [m] (assoc m :foo 3))
(defcall foo four :miss [m] (assoc m :foo 4))

(fact (set (foo {:v :hit}))
  => #{{:v :hit :foo 1} {:v :hit :foo 2} {:v :hit :foo 3}})

(defmulticall bar :v)
(defcall bar :hit [m] (assoc m :bar 1))
(defcall bar :hit [m] (assoc m :bar 2))
(defcall bar one :hit [m] (assoc m :bar 3))
(defcall bar one :hit [m] (assoc m :bar 4))
