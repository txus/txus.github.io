(ns blog.core
  (:require [optimus.assets :as assets]
            [optimus.export]
            [optimus.optimizations :as optimizations]
            [optimus.prime :as optimus]
            [optimus.link :as link]
            [optimus.strategies :refer [serve-live-assets]]
            [blog.highlight :refer [highlight-code-blocks]]
            [blog.post :as post]
            [blog.rss :as rss]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [me.raynes.cegdown :as md]
            [stasis.core :as stasis]
            [hiccup.page :refer [html5]]))

(def analytics-id "UA-19362711-1")

(defn layout-page [request page]
  (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1.0"}]
     [:title "the programmer language,"]
     [:link {:rel "alternate" :title "ATOM" :type "application/atom+xml" :href "/atom.xml"}]
     [:link {:rel "stylesheet" :href (link/file-path request "/styles.css")}]
     [:script {:type "text/javascript"}
      """
      (function(f,b){if(!b.__SV){var a,e,i,g;window.mixpanel=b;b._i=[];b.init=function(a,e,d){function f(b,h){var a=h.split(\".\");2==a.length&&(b=b[a[0]],h=a[1]);b[h]=function(){b.push([h].concat(Array.prototype.slice.call(arguments,0)))}}var c=b;\"undefined\"!==typeof d?c=b[d]=[]:d=\"mixpanel\";c.people=c.people||[];c.toString=function(b){var a=\"mixpanel\";\"mixpanel\"!==d&&(a+=\".\"+d);b||(a+=\" (stub)\");return a};c.people.toString=function(){return c.toString(1)+\".people (stub)\"};i=\"disable track track_pageview track_links track_forms register register_once alias unregister identify name_tag set_config people.set people.set_once people.increment people.append people.track_charge people.clear_charges people.delete_user\".split(\" \");
      for(g=0;g<i.length;g++)f(c,i[g]);b._i.push([a,e,d])};b.__SV=1.2;a=f.createElement(\"script\");a.type=\"text/javascript\";a.async=!0;a.src=\"//cdn.mxpnl.com/libs/mixpanel-2-latest.min.js\";e=f.getElementsByTagName(\"script\")[0];e.parentNode.insertBefore(a,e)}})(document,window.mixpanel||[]);
      mixpanel.init(\"af522f005afba4a9815d19dc53e50261\");
      """]]

    [:body
     [:div.container
       [:h1 [:a {:href "/"} "the programmer language,"]]
       [:div.body page]
       [:section {:id "footer"}
        [:hr]
        [:img {:src "https://pbs.twimg.com/profile_images/541551337779908609/j6MXtyhj.jpeg" :width 60 :height 60}]
        [:span "I'm "]
        [:a {:href "https://twitter.com/txustice"} "@txustice"]
        [:span " on Twitter. Follow me and say hello! Or go read some of my "]
        [:a {:href "/"} "other posts"]
        [:span "."]
        [:hr]]]]))

(defn markdown-posts [posts]
  (reduce (fn [acc post]
            (conj acc { (post :url) (post/to-markdown layout-page post) })) {} posts))

(defn markdown-pages [pages]
  (zipmap (map #(-> %
                    (str/replace #"\.md$" "")
                    (str/replace #"\.markdown$" ""))
               (keys pages))
          (map #(fn [req] (layout-page req (md/to-html % [:autolinks :fenced-code-blocks :strikethrough])))
               (vals pages))))

(defn markdown-index [index posts]
  (let [render-index (fn [req]
                       (let [rendered-posts (->> posts
                                                 (map (fn [post] ((post/to-markdown (fn [_ page] page) post) req)))
                                                 (interpose "<hr/>")
                                                 (reduce str)
                                                 (str (md/to-html index [:autolinks])))]
                         (layout-page req rendered-posts)))]
    {"/index.html" render-index}))

(defn prepare-page [page req]
  (-> (if (string? page) page (page req))
      highlight-code-blocks))

(defn get-raw-pages []
  (let [post-map (stasis/slurp-directory "resources/posts" #"\.(md|markdown)$")
        posts (->> post-map
                   (map (fn [[k v]] [k v]))
                   (sort-by (fn [[k v]] k))
                   reverse
                   (map (fn [[k v]] (post/from-markdown k v))))]
    (stasis/merge-page-sources
      {:public
       (stasis/slurp-directory "resources/public" #".*\.(html|css|js)$")
       :index
       (markdown-index (slurp "resources/index.md") posts)
       :partials
       (markdown-pages (stasis/slurp-directory "resources/partials" #".*\.(md|markdown)"))
       :posts
       (markdown-posts posts)
       :rss
       { "/atom.xml" (rss/atom-xml posts) }})))

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
