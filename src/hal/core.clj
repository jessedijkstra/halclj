(ns hal.core
  (:refer-clojure :rename {get-in clj-get-in, get clj-get})
  (:require [clojure.data.json :as json]
            [clj-http.client :as http]))

(defn- request
  [url]
  (json/read-json (:body (http/get url))))

(defn- determine
  "Determine if URL is given or datastructure. If an URL is given request datastructure, else return data structure"
  [hal]
  (if (string? hal)
    (request hal)
    hal))

(defn get-link
  "Get a link from a HAL data structure"
  [hal key]
  (clj-get-in (determine hal) [:_links key :href]))

(defn get-embed
  "Get an embed from a HAL data structure"
  [hal key]
  (clj-get-in (determine hal) [:_embedded key]))

(defn fetch
  "Fetch a resource from the server"
  ([url]
   (request url))
  ([hal resource]
   (request (get-link (determine hal) resource))))

(defn get
  "Get a linked resource from a HAL data structure"
  ([hal key]
    (let [hal (determine hal)
          link (get-link hal key)
          embed (get-embed hal key)]
      (if (nil? embed)
        (fetch link)
        embed)))
  ([url]
   (fetch url)))

(defn get-in
  "Get a deep linked resource from a HAL data structure"
  [hal keys]
   (let [hal (determine hal)
         value (get hal (first keys))]

       (if (= (count keys) 1)
         value
         (get-in value (rest keys)))))
