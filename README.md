# pidge

An attempt at an abstraction for arbitrary object ordering. The initial target backend for persistence is 
redis' sorted sets. 

The motivation for this came from struggling with performance issues in trying to arbitrarily order 1M+ 
user generated content in an RDMS. Sorting and filtering a relation of that size on a high traffic website is problematic 
because it typically involves joins to related tables to determine content status and joins to yet other tables to determine
object scoring or sort order. Initializing the sorted set once, than updating membership and rank at write enables O(log(N)+M) 
retrieval for the top M.




## Usage

Add the following to your project.clj <code>[org.clojars.drsnyder/pidge "0.0.1"]</code>.
    
    ; in ns
    (:use [pidge.sort]
          [pidge.data.redis])
    (:require [redis.core :as redis])

    ; importing
    (def x 
      (time 
        (update 
          (new-container "test") 
          (fn [c] (dorun 
                    (for [x (range 1 1000000)] 
                      ;                   id  score
                      (add c (new-sortable x (+ x 1)))))) 
          {:redis-server redis-server})))
    "Elapsed time: 25497.341 msecs"
  
    ; fetch the object ids for the top 100; compare to SQL SELECT ... JOIN ... JOIN ... WHERE ... ORDER BY
    (def r 
      (time 
        (with (new-container "test") 
              (fn [c] (top c 100)) 
              {:redis-server redis-server})))
    "Elapsed time: 3.762 msecs"

    ; fetch a page in the middle
    (def r 
      (time 
        (with (new-container "test") 
              (fn [c] (page c 500000 500010)) 
              {:redis-server redis-server})))
    "Elapsed time: 4.29 msecs"

## Testing

If your redis server is running on something other than 127.0.0.1:6379, you can change
that by:

export REDIS_HOST=127.0.0.1 
export REDIS_PORT=6379

## License

Copyright (C) 2012 Damon Snyder 

Distributed under the Eclipse Public License, the same as Clojure.
