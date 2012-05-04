(ns pidge.test.store.redis
  (:use [clojure.test]
        [pidge.sort]
        [pidge.store.redis]))

(def test-key "pidge.test.store.redis")

(deftest test-container 
         (let [c (new-container "abc")]
           (is (= (.key c) "abc"))
           (is (instance? pidge.store.redis.RedisContainer c))
           (is (satisfies? Container c))))
