(ns pidge.sort)

(defprotocol Sortable
  (ident [object])
  (score [object]))

(defprotocol Container
             ; make score and ident a "Sortable"
             (add  [object #^pidge.sort.Sortable data])
             (top  [object n])
             (card [object])
             (with [object f &[params]])
             ; index?
             ; reverse take?
             )


(deftype SortableObject [ident score])

(extend-type SortableObject
             Sortable
             (ident [this] (Integer. (.ident this)))
             (score [this] (Integer. (.score this))))


(defn new-sortable [ident score]
  (SortableObject. ident score))


