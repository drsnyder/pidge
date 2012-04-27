(ns pidge.test.store.redis-with
  (:use [clojure.test]
        [pidge.sort]
        [pidge.store.redis])
  (:require [redis.core :as redis]))

(def redis-server {
                   :host (get (System/getenv) "REDIS_HOST" "127.0.0.1") 
                   :port (Integer/parseInt (get (System/getenv) "REDIS_PORT" 6379))
                   }) 
(def test-key "pidge.test.store.redis-with")

(deftest test-update
  (let [t10 (with (new-container test-key)  
                   (fn [c] (top c 10)) {:redis-server redis-server})]
    (do
      (update (new-container test-key)
              (fn [c] 
                (dorun (for [x (range 1 100)]
                         (add c (new-sortable x (+ x 1))))))
              {:redis-server redis-server})
      (is (=  (ident (first t10)))))))

