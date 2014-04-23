(ns blog.core
  (:require [optimus.assets :as assets]
            [optimus.export]
            [optimus.optimizations :as optimizations]
            [optimus.prime :as optimus]
            [optimus.link :as link]
            [optimus.strategies :refer [serve-live-assets]]
            [blog.highlight :refer [highlight-code-blocks]]
            [blog.post :as post]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [me.raynes.cegdown :as md]
            [stasis.core :as stasis]
            [hiccup.page :refer [html5]]))

(defn layout-page [request page]
  (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1.0"}]
     [:title "the programmer language,"]
     [:link {:rel "stylesheet" :href (link/file-path request "/styles.css")}]]

    [:body
     [:div.container
       [:h1 "the programmer language,"]
       [:div.body page]]]))

(defn markdown-posts [pages]
  (->> pages
      (map (fn [[k v]] (post/from-markdown k v)))
      (reduce (fn [acc post]
                (println (post :url))
                (conj acc { (post :url) (post/to-markdown layout-page post) })) {})))

(defn markdown-pages [pages]
  (zipmap (map #(-> %
                    (str/replace #"\.md$" "")
                    (str/replace #"\.markdown$" ""))
               (keys pages))
          (map #(fn [req] (layout-page req (md/to-html % [:autolinks :fenced-code-blocks :strikethrough])))
               (vals pages))))

(defn prepare-page [page req]
  (-> (if (string? page) page (page req))
      highlight-code-blocks))

(defn get-raw-pages []
  (stasis/merge-page-sources
    {:public
     (stasis/slurp-directory "resources/public" #".*\.(html|css|js)$")
     :index
     (markdown-pages {"/index.html" (slurp "resources/index.md")})
     :partials
     (markdown-pages (stasis/slurp-directory "resources/partials" #".*\.(md|markdown)"))
     :posts
     (markdown-posts (stasis/slurp-directory "resources/posts" #"\.(md|markdown)$"))}))

(defn prepare-pages [pages]
  (zipmap (keys pages)
          (map #(partial prepare-page %) (vals pages))))

(defn get-pages []
  (prepare-pages (get-raw-pages)))

(defn get-assets []
  (assets/load-assets "public" [#".*"]))

(def app (stasis/serve-pages get-pages))

(def app
  (optimus/wrap (stasis/serve-pages get-pages)
                get-assets
                optimizations/all
                serve-live-assets))

(def export-dir "output")

(defn export []
  (let [assets (optimizations/all (get-assets) {})]
    (stasis/empty-directory! export-dir)
    (optimus.export/save-assets assets export-dir)
    (stasis/export-pages (get-pages) export-dir {:optimus-assets assets})))
