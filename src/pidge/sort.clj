(ns pidge.sort
  (:import [java.lang.reflect Method])) 

(defprotocol Sortable
  (ident [object])
  (score [object]))

(defprotocol Container
             ; make score and ident a "Sortable"
             (add    [object #^pidge.sort.Sortable data])
             (top    [object n])
             (page   [object start stop])
             (card   [object])
             (index  [object v])
             (with   [object #^Method f params])
             (update [this #^Method f params]))


(deftype SortableObject [ident score])

(extend-type SortableObject
             Sortable
             (ident [this] (Integer. (.ident this)))
             (score [this] (Integer. (.score this))))


(defn new-sortable [ident score]
  (SortableObject. ident score))


