(defproject org.clojars.drsnyder/pidge "0.0.1"
  :description "Real-time object ordering"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojars.tavisrudd/redis-clojure "1.3.1"]]
  :dev-dependencies [[midje "1.3.1"]]
  :repl-init pidge.repl-helper
  :test-selectors {:default (complement :integration)
                   :integration :integration
                   :all (fn [_] true)}
  :jvm-opts ["-Xmx2g" "-server"])
