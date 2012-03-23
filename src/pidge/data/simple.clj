(ns pidge.data.simple
  (:use [pidge.sort]))


(extend-type clojure.lang.PersistentTreeMap
             Container
             (add   [this data]
                  (assoc this (score data) data))
             (top [this n] (map #(val %) (take n this)))
             (card [this] (count this)))

(defn new-container []
  (sorted-map))


