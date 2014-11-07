(ns hal.core-spec
  (:require [speclj.core :refer :all]
            [vcr-clj.clj-http :refer [with-cassette]]
            [hal.core :as hal]))

(def api_url "https://static.blendle.nl/api.json")

(defn has-keys? [m keys]
    (every? (partial contains? m) keys))

(describe "Fetch HAL"
          (it "should fetch api.json and its self url should be the same and input url (hal/fetch and hal/get-link)"
              (with-cassette :api
                (let [api (hal/get api_url)]
                      (should (= (hal/get-link api :self) 
                              api_url)))))
          
          (it "should get publications (hal/get embedded)"
              (with-cassette :publications
                (let [publications (hal/get api_url :publications)]
                  (should (= (hal/get-link publications :self) 
                             "https://static.blendle.nl/publications.json")))))

          (it "should fetch all_posts and match the number of results in array (hal/get linked)"
              (with-cassette :all_posts
                (let [posts (hal/get api_url :all_posts)] 
                  (should (= (count (get-in posts [:_embedded :posts])) 
                             (get posts :results))))))

          (it "should get a valid issue from most_recent from publications (hal/get-in linked and embedded)"
              (with-cassette :issues
                (let [issues (hal/get-in api_url [:publications :most_recent :issues])]
                  (should (true? (has-keys? (first issues) [:format_version :representations :id :provider :initial_publication_time :date :items]))))))
)

(run-specs)
