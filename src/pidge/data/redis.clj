(ns pidge.data.redis
  (:use [pidge.sort])
  (:require [redis.core :as redis]))

(deftype RedisContainer [#^String key]) 

(defn pair [s]
  (loop [c []
         r s]
    (if (> (count r) 0) 
      (recur (conj c (list (first r) (first (rest r)))) (rest (rest r)))
      c)))


(extend-type RedisContainer
             Container
             (add [this data]
                  (do
                    (redis/zadd (.key this) (score data) (ident data))
                    this))
             (top [this n] (doall
                             (map #(new-sortable (first %) (second %)) 
                                  (pair (redis/zrange (.key this) 0 n "withscores")))))
             (card [this] (redis/zcard (.key this)))
             (with [this f [&params]])
             )

(defn new-container [k]
  (RedisContainer. k))

