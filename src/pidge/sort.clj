(ns pidge.sort
  (:import [java.lang.reflect Method])) 

(defprotocol Sortable
  (ident [object])
  (score [object]))

; data structure or container that maintains the sort order
(defprotocol Container
             (add    [object #^pidge.sort.Sortable data])

             ; start and stop are indexes
             (page   [object start stop #^Keyword dir] [object start stop])

             ; top starts at index 0 and returns 0 - (n - 1)
             (top    [object n #^Keyword dir] [object n])
             (card   [object])
             (index  [object v #^Keyword dir] [object v])

             (update [object #^Method f]))


(deftype SortableObject [ident score])

(extend-type SortableObject
             Sortable
             (ident [object] (Integer. (.ident object)))
             (score [object] (Integer. (.score object))))


(defn new-sortable [ident score]
  (SortableObject. ident score))


