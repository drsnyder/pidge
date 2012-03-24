(ns pidge.data.redis
  (:use [pidge.sort]
        [redis.pipeline :only (pipeline)])
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
             (add    [this data]
                  (do
                    (redis/zadd (.key this) (score data) (ident data))
                    this))
             (top    [this n] (doall
                             (map #(new-sortable (first %) (second %)) 
                                  (pair (redis/zrevrange (.key this) 0 n "withscores")))))
             (page   [this start stop] (doall
                             (map #(new-sortable (first %) (second %)) 
                                  (pair (redis/zrevrange (.key this) start stop "withscores")))))
             (index  [this v] (redis/zrevrank (.key this) v))
             (card   [this] (redis/zcard (.key this)))

             (with   [this f params]
                   (redis/with-server (get params :redis-server)
                                          (f this)))
             (update [this f params]
                   (redis/with-server (get params :redis-server)
                                      (pipeline
                                          (f this)))))

(defn new-container [k]
  (RedisContainer. k))

