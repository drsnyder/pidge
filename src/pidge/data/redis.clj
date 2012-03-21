(ns pidge.data.redis
  (:use [pidge.data]
        [pidge.sort])
  (:require [redis.core :as redis]))

(deftype RedisContainer [#^String key]) 

(extend-type RedisContainer
             Container
             (add [this data]
                  (do
                    (redis/zadd (.key this) (score data) (ident data))
                    this))
             (top [this n] (redis/zrange (.key this) 0 n))
             (card [this] (redis/zcard (.key this))))

(defn new-container [k]
  (RedisContainer. k))

