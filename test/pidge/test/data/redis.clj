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
      (add (pdr/new-container test-key) 
           {:id 111 :score 15})
      {:id 112 :score 12})
    {:id 9 :score 333}))

(defn tear-down []
    (redis/del test-key))

(defn redis-fixture [f]
  (redis/with-server redis-server 
                     (setup)
                     (f)
                     (tear-down)))

(use-fixtures :each redis-fixture)



(deftest test-card
         (is (= (card (pdr/new-container test-key)) 3)))

(deftest test-top
         (is false))
