(ns pidge.test.store.redis-update
  (:use [clojure.test]
        [pidge.sort]
        [pidge.store.redis])
  (:require [redis.core :as redis]))

(def redis-server {
                   :host (get (System/getenv) "REDIS_HOST" "127.0.0.1") 
                   :port (Integer/parseInt (get (System/getenv) "REDIS_PORT" 6379))
                   }) 
(def test-key "pidge.test.store.redis-with")


(defn redis-fixture [f]
  (redis/with-server redis-server 
                     (f)))

(use-fixtures :each redis-fixture)

(deftest test-update
  (let [c (new-container test-key)]
    (do
      (update (new-container test-key)
              (fn [c] 
                (dorun (for [x (range 1 100)]
                         (add c (new-sortable x (+ x 1)))))))
      (is (=  (ident (first (top c 10))) 99))
      (is (=  (ident (second (top c 10))) 98)))))
