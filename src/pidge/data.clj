(ns pidge.data
  (:use [pidge.data]
       [pidge.sort]))

;http://fiji.sc/javadoc/clojure/lang/package-tree.html
(extend-type clojure.lang.APersistentMap
             Sortable
             (ident [this] (get this :id))
             (score [this] (get this :score)))

(defprotocol Container
             ; make score and ident a "Sortable"
             (add  [object #^pidge.sort.Sortable data])
             (top  [object n])
             (card [object])
             ; index?
             ; reverse take?
             )
