(ns pidge.data.simple
  (:use [pidge.data]
        [pidge.sort]))


(extend-type clojure.lang.PersistentTreeMap
             Container
             (add   [this data]
                  (assoc this (score data) (ident data)))
             (top [this n] (take n this))
             (card [this] (count this)))

(defn new-container []
  (sorted-map))


