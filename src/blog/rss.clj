(ns blog.rss
  (:require [me.raynes.cegdown :as md]
            [blog.post :as p]
            [clojure.data.xml :as xml]))

(defn- entry [post]
  [:entry
   [:title (:title post)]
   [:updated (:date post)]
   [:author [:name (:author post)]]
   [:link {:href (str "http://blog.txus.io" (:path post))}]
   [:id (str "urn:blog.txus.io:feed:post:" (:title post))]
   [:content {:type "html"} (md/to-html (:body post) p/pegdown-options)]])

(defn atom-xml [posts]
  (xml/emit-str
   (xml/sexp-as-element
    [:feed {:xmlns "http://www.w3.org/2005/Atom"}
     [:id "urn:blog.txus.io:feed"]
     [:updated (-> posts first :date)]
     [:title {:type "text"} "the programmer language,"]
     [:link {:rel "self" :href "http://blog.txus.io/atom.xml"}]
     (map entry posts)])))
