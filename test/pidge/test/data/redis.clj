(ns pidge.test.data.redis
  (:use [pidge.data]
        [clojure.test])
  (:require [redis.core :as redis]
            [pidge.data.redis :as pdr]))

(def redis-server {:host "127.0.0.1" :port 6379}) 
(def test-key "pidge.test.data.redis")

; TODO do this with fixtures
(defn setup []
  (add 
    (add 
      (add (pdr/RedisContainer. test-key) 
           {:id 111 :score 15})
      {:id 112 :score 12})
    {:id 9 :score 333}))

(defn tear-down []
    (redis/del test-key))

(deftest test-card
         (redis/with-server redis-server 
                            (setup)
                            ; to fix add redis/with-server
                            (is (= (card (pdr/RedisContainer. test-key)) 3))
                            (tear-down)))
