(ns pidge.data.simple
  (use [pidge.data]))


;(deftype SimpleDatastore [#^clojure.lang.PersistentTreeMap hash])

(extend-type clojure.lang.PersistentTreeMap
             Container
             (add   [this score ident]
                  (assoc this score ident))
             (top [this n] (take n this)))
;(extend-protocol Container
;                 clojure.lang.PersistentTreeMap
;                 (add   [this score ident]
;                      (assoc this score ident))
;                 (top [this n] (take n this)))
