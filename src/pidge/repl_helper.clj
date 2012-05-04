(ns pidge.repl-helper
  (:use [pidge.sort])
  (:use [pidge.bridge])
  (:use [pidge.store.redis])
  (:use [redis.pipeline :only (pipeline)])
  (:require [clojure.test :as testing])
  (:require [redis.core :as redis]))

(def redis-server {
                   :host (get (System/getenv) "REDIS_HOST" "127.0.0.1") 
                   :port (Integer/parseInt (get (System/getenv) "REDIS_PORT" 6379))
                   }) 

;(with-redefs-fn
;  {#'pidge.sort/add (fn [t d] (do (println "here") t))} 
;  (redis/with-server redis-server 
;                     #(add (new-container "test-1") (new-sortable 111 15)))
;  ) 
;
;(with-redefs-fn
;  {#'pidge.store.redis/redefs-test (fn [v] (println "here" v)) } 
;  #(redefs-test "a"))
;
;(testing/run-tests 'pidge.test.store.redis)
