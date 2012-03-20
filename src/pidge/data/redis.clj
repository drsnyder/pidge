(ns pidge.data.redis
  (:use [pidge.data]
        [pidge.sort])
  (:require [redis.core :as redis]))

(deftype RedisContainer [#^String key]) 


(extend-type clojure.lang.PersistentArrayMap
             Sortable
             (ident [this] (get this :id))
             (score [this] (get this :score)))

(extend-type clojure.lang.PersistentHashMap
             Sortable
             (ident [this] (get this :id))
             (score [this] (get this :score)))


;(def rs {:host "127.0.0.1" :port 6379}) 
;(redis/with-server rs 
;                   (add 
;                     (add 
;                       (add (pidge.data.redis.RedisContainer. "test_set") 
;                            {:id 111 :score 15})
;                       {:id 112 :score 12})
;                     {:id 9 :score 333}))

(extend-type RedisContainer
             Container
             (add [this data]
                  (do
                    (redis/zadd (.key this) (score data) (ident data))
                    this))
             (top [this n] (redis/zrange (.key this) 0 n))
             (card [this] (redis/zcard (.key this))))




