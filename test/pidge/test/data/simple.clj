(ns pidge.test.data.simple
  (:use [pidge.data]
        [pidge.data.simple])
  (:use [clojure.test]))

(def simplecontainer 
  (add 
    (add 
      (add (sorted-map) 
           {:id 111 :score 15})  ; second
      {:id 112 :score 12})       ; first
    {:id 9 :score 99}))          ; third

(deftest test-card
         (is (= (card simplecontainer) 3)))

(deftest test-top
         (is (= (first (first simplecontainer)) 12)))
