(ns pidge.sort
  (:import [java.lang.reflect Method])) 

(defprotocol Sortable
  (ident [object])
  (score [object]))

; data structure or container that maintains the sort order
(defprotocol Container
             (add    [object #^pidge.sort.Sortable data])
             (page   [object start stop #^Keyword dir] [object start stop])
             (top    [object n #^Keyword dir] [object n])
             (card   [object])
             (index  [object v #^Keyword dir] [object v])

             (with   [object #^Method f params] [object #^Method f])
             (update [object #^Method f params] [object #^Method f]))

; bridge that connects the persistent storage (e.g. RDBMS with the container)
(defprotocol Bridge
             (data-page [object start stop #^Keyword dir] [object start stop])
             (position  [object v #^Keyword dir] [object v]))


(deftype SortableObject [ident score])

(extend-type SortableObject
             Sortable
             (ident [object] (Integer. (.ident object)))
             (score [object] (Integer. (.score object))))


(defn new-sortable [ident score]
  (SortableObject. ident score))


