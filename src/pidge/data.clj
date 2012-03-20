(ns pidge.data
  (:use [pidge.data]
       [pidge.sort]))

(defprotocol Container
             ; make score and ident a "Sortable"
             (add  [object #^pidge.sort.Sortable data])
             (top  [object n])
             (card [object])
             ; index?
             ; reverse take?
             )
