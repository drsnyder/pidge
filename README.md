# pidge

An attempt at an abstraction for arbitrary object ordering. The initial target backend for persistence is 
redis' sorted sets. 

The motivation for this came from struggling with performance issues in trying to arbitrarily order 1M+ 
user generated content in an RDMS. Sorting and filtering a relation of that size on a high traffic website is problematic 
because it typically involves joins to related tables to determine content status and joins to yet other tables to determine
object scoring or sort order. Initializing the sorted set once, than updating membership and rank at write enables O(log(N)+M) 
retrieval for the top M.


## Usage

FIXME: write

## License

Copyright (C) 2012 Damon Snyder 

Distributed under the Eclipse Public License, the same as Clojure.
