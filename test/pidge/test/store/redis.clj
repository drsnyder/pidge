(ns pidge.test.store.redis
  (:use [clojure.test]
        [pidge.sort]
        [pidge.store.redis])
  (:require [redis.core :as redis]))

(def redis-server {:host "127.0.0.1" :port 6379}) 
(def test-key "pidge.test.store.redis")

; TODO do this with fixtures
(defn setup []
  (add 
    (add 
      (add (new-container test-key) 
           (new-sortable 111 15))  ; second
      (new-sortable 112 12))       ; first
    (new-sortable 9 99)))          ; third

(defn tear-down []
    (redis/del test-key))

(defn redis-fixture [f]
  (redis/with-server redis-server 
                     (setup)
                     (f)
                     (tear-down)))

(use-fixtures :each redis-fixture)



(deftest test-card
         (is (= (card (new-container test-key)) 3)))

(deftest test-index
         (is (= (index (new-container test-key) 112) 2))
         (is (= (index (new-container test-key) 111) 1))
         (is (= (index (new-container test-key) 9) 0)))

(deftest test-index-asc
         (is (= (index (new-container test-key) 112 :asc) 0))
         (is (= (index (new-container test-key) 111 :asc) 1))
         (is (= (index (new-container test-key) 9   :asc) 2)))

(deftest test-top
         (is (= (score 
                  (first 
                    (top (new-container test-key) 1))) 99))
         (is (= (ident 
                  (first 
                    (top (new-container test-key) 1))) 9)))

(deftest test-page
         (is (= (ident 
                  (first
                      (page (new-container test-key) 0 2))) 9))
         (is (= (ident 
                  (second
                      (page (new-container test-key) 0 2))) 111)))

(deftest test-page-asc
         (is (= (ident 
                  (first
                      (page (new-container test-key) 0 2 :asc))) 112))
         (is (= (ident 
                  (second
                      (page (new-container test-key) 0 2 :asc))) 111)))
