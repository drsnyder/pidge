(ns pidge.store.redis
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

(defn- do-page
  [#^pidge.sort.Container c start stop dir]
  (let [rrangefn (if (and dir (= dir :desc)) redis/zrevrange redis/zrange)]
    (doall
      (map #(new-sortable (first %) (second %)) 
           (pair (rrangefn (.key c) start stop "withscores"))))))


(defn- do-update [c f]
  (dorun 
    (pipeline
      (f c))))

(extend-type RedisContainer
             Container
             (add    [this data]
                  (do
                    (redis/zadd (.key this) (score data) (ident data))
                    this))

             (page   ([this start stop dir] (do-page this start stop dir))
                     ([this start stop] (do-page this start stop :desc)))

             (top    ([this n dir] (do-page this 0 n dir))
                     ([this n] (do-page this 0 n :desc)))

             (index  ([this v dir] 
                      (let [rrankfn (if (= dir :desc) redis/zrevrank redis/zrank)]
                        (rrankfn (.key this) v)))
                     ([this v] (redis/zrevrank (.key this) v)))

             (card   [this] (redis/zcard (.key this)))

             (update [this f] (do-update this f)))

(defn new-container [k]
  (RedisContainer. k))

