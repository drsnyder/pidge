(ns pidge.test.store.redis-with
  (:use [clojure.test]
        [pidge.sort]
        [pidge.store.redis])
  (:require [redis.core :as redis]))

(def redis-server {:host "127.0.0.1" :port 6379}) 
(def test-key "pidge.test.store.redis-with")

(deftest test-update
  (let [u (update (new-container test-key)
                  (fn [c] 
                    (dorun (for [x (range 1 100)]
                      (add c (new-sortable x (+ x 1)))))))
        t10 (with (new-container test-key)  
                   (fn [c] (top c 10)) {:redis-server redis-server})]
    (is (= 99 (count u)))
    (is (=  (ident (first t10))))))

