(ns pidge.sort
  (:import [java.lang.reflect Method])) 

(defprotocol Sortable
  (ident [object])
  (score [object]))

(defprotocol Container
             (add    [object #^pidge.sort.Sortable data])
             (page   [object start stop #^Keyword dir] [object start stop])
             (top    [object n #^Keyword dir] [object n])
             (card   [object])
             (index  [object v #^Keyword dir] [object v])

             (with   [object #^Method f params] [object #^Method f])
             (update [object #^Method f params] [object #^Method f]))


(deftype SortableObject [ident score])

(extend-type SortableObject
             Sortable
             (ident [object] (Integer. (.ident object)))
             (score [object] (Integer. (.score object))))


(defn new-sortable [ident score]
  (SortableObject. ident score))


