(ns pidge.bridge
  (:use [pidge.sort])
  (:import [java.lang.reflect Method])) 

  ; start and stop are indexes
  ; signature of f [<seq of Sortable>] => sorted data

(defn data-page [#^pidge.sort.Container c start stop #^Method f &[#^Keyword dir]]
  (let [objects (with c (fn [c] (page c start stop dir)))
        ids (map #(ident %) objects)
        scores (map #(score %) objects)]
    (f ids scores)))

(defn data-position [#^pidge.sort.Container c v &[#^Keyword dir]]
  (with c (fn [c] (index c v dir))))

