(ns pidge.test.bridge
  (:use [clojure.test]
        [pidge.sort]
        [pidge.bridge]
        [pidge.store.redis])
  (:require [redis.core :as redis]))

; TODO: figure out some kind of mock for pidge.store.redis

(def redis-server {:host "127.0.0.1" :port 6379}) 
(def test-key "pidge.test.bridge")

(defn setup []
  (update 
    (new-container test-key) 
    (fn [c] (dorun 
              (for [x (range 1 10)] 
                ;                   id  score
                (add c (new-sortable x (+ x 1)))))) 
    {:redis-server redis-server}))


(defn tear-down []
    (redis/del test-key))

(defn redis-fixture [f]
  (redis/with-server redis-server 
                     (setup)
                     (f)
                     (tear-down)))

(use-fixtures :each redis-fixture)

(deftest test-data-page 
         (let [ii (data-page (new-container test-key) 0 10 (fn [i s] i))
               ss (data-page (new-container test-key) 0 10 (fn [i s] s))]
           (is (= (list 1 2 3 4 5 6 7 8 9 10)) ii)
           (is (= (list 2 3 4 5 6 7 8 9 10 11)) ss)))

(deftest test-data-position
         (is (= 0 (data-position (new-container test-key) 1)))
         (is (= 1 (data-position (new-container test-key) 2)))
         (is (= 8 (data-position (new-container test-key) 9))))





