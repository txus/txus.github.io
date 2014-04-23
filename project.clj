(defproject blog "0.1.0-SNAPSHOT"
  :description "blog.txus.io source code"
  :url "https://github.com/txus/txus.github.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [stasis "1.0.0"]
                 [ring "1.2.1"]
                 [hiccup "1.0.5"]
                 [me.raynes/cegdown "0.1.1"]
                 [enlive "1.1.5"]
                 [clygments "0.1.1"]
                 [optimus "0.14.2"]
                 [clj-yaml "0.4.0"]
                 [clj-time "0.7.0"]]
  :ring {:handler blog.core/app}
  :aliases {"build-site" ["run" "-m" "blog.core/export"]}
  :profiles {:dev {:plugins [[lein-ring "0.8.10"]]}
             :test {:dependencies [[midje "1.6.0"]]
                    :plugins [[lein-midje "3.1.3"]]}})
