(ns pidge.data.simple
  (:use [pidge.data]
        [pidge.sort]))


; any container should define what the input should be
; FIXME: is there a way to capture both of these?
(extend-type clojure.lang.PersistentArrayMap
             Sortable
             (ident [this] (get this :id))
             (score [this] (get this :score)))

(extend-type clojure.lang.PersistentHashMap
             Sortable
             (ident [this] (get this :id))
             (score [this] (get this :score)))

; a simple lowest to highest container
;(add 
;  (add 
;    (add (sorted-map) 
;         {:id 111 :score 15}) 
;    {:id 112 :score 12}) 
;  {:id 9 :score 333})
(extend-type clojure.lang.PersistentTreeMap
             Container
             (add   [this data]
                  (assoc this (score data) (ident data)))
             (top [this n] (take n this))
             (card [this] (count this)))



