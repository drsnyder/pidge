(def redis-server {:host "127.0.0.1" :port 6379}) 
(def x 
  (time 
    (update 
      (new-container "test-1m-1") 
      (fn [c] (dorun 
                (for [x (range 1 1000000)] 
                  ;                   id  score
                  (add c (new-sortable x (+ x 1)))))) 
      {:redis-server redis-server})))

(def x 
  (time 
    (update 
      (new-container "test-4m-1") 
      (fn [c] (dorun 
                (for [x (range 1 4000000)] 
                  ;                   id  score
                  (add c (new-sortable x (+ x 1)))))) 
      {:redis-server redis-server})))

(def x 
  (time 
    (update 
      (new-container "test-10m") 
      (fn [c] (dorun 
                (for [x (range 1 10000000)] 
                  ;                   id  score
                  (add c (new-sortable x (+ x 1)))))) 
      {:redis-server redis-server})))

(defn create-n-sorted-sets [prefix n sze]
  (for [x (range 0 n)]
    (update
      (new-container (format "%s-%d-%d" prefix n x))
      (fn [c] (dorun 
                (for [x (range 0 sze)] 
                  ;                   id  score
                  (add c (new-sortable x (+ x 1)))))))))

; ocn: 1.2M threads. avg post count 14
; 120k object_display_order entries; avg size 0.6
; (create-n-sorted-sets "testocn" 1200000 14) => 250MB
