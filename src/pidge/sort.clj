(ns pidge.sort)

(defprotocol Sortable
  (container-key [object])
  (ident [object])
  (score [object]))

(defprotocol Persistable
  (store [object #^pidge.data.Container c]))

;(deftype SortableObject [ident weight])

;(extend-type SortableObject
;             Sortable
;             Persistable
;             (#^String container-key [this] "somekey")
;             (ident                  [this] (.ident this))
;             (weight                 [this] (.weight this))
;             (store                  [this #^pidge.data.Container c] (.add 
;                                                                       (container-key this) 
;                                                                       (score this)
;                                                                       (ident this))))
