(ns pidge.test.data.simple
  (:use [clojure.test]
        [pidge.sort]
        [pidge.data.simple]))

(def simplecontainer 
  (add 
    (add 
      (add (new-container) 
           (new-sortable 111 15))  ; second
      (new-sortable 112 12))       ; first
    (new-sortable 9 99)))          ; third

(deftest test-card
         (is (= (card simplecontainer) 3)))

(deftest test-top
         (is (= (score (first (top simplecontainer 1))) 12)))
