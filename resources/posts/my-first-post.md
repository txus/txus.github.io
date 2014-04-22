# My first post

It's pretty short for now.

Go [here](about).

```clj
(defproject blog "0.1.0-SNAPSHOT"
  :description "blog.txus.io source code"
  :url "https://github.com/txus/txus.github.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.8.10"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [stasis "1.0.0"]
                 [ring "1.2.1"]
                 [hiccup "1.0.5"]
                 [me.raynes/cegdown "0.1.1"]]
  :ring {:handler blog.core/app})
```
