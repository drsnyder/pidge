(ns pidge.data)

(defprotocol Container
             (add  [object score ident])
             (top [object n])
             ; index?
             ; reverse take?
             )
