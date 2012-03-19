(ns pidge.data)

(defprotocol Container
             ; make score and ident a "Sortable"
             (add  [object score ident])
             (top [object n])
             ; index?
             ; reverse take?
             )
