(ns pidge.test.data.simple
  (:use [pidge.data]
        [pidge.data.simple]
        [clojure.test])
  (:require [pidge.data.simple :as pds]))

(def simplecontainer 
  (add 
    (add 
      (add (pds/new-container) 
           {:id 111 :score 15})  ; second
      {:id 112 :score 12})       ; first
    {:id 9 :score 99}))          ; third

(deftest test-card
         (is (= (card simplecontainer) 3)))

(deftest test-top
         (is (= (first (first simplecontainer)) 12)))
