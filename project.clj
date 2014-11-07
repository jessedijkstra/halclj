(defproject hal "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]
                 [clj-http "1.0.0"]]
  :profiles {:dev {:dependencies [[speclj "3.1.0"]
                                  [com.gfredericks/vcr-clj "0.3.3"]]}}
  :plugins [[speclj "3.1.0"]] 
  :test-paths ["spec"]
  :main hal.core)
