(defproject lambdaforge "0.1.0-SNAPSHOT"
  
  :description "Online presence"
  
  :url "https://github.com/kordano/lambdaforge"
  
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :source-paths ["src/client" "src/server"]
  
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122"]
                 [org.omcljs/om "1.0.0-alpha22"]
                 [org.clojure/core.async "0.2.374"]
                 [cljsjs/react-with-addons "0.14.3-0"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [com.cemerick/piggieback "0.2.1"]]

  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-doo "0.1.5"]
            [lein-figwheel "0.4.1"]
            [lein-sassy "1.0.7"]
            [lein-npm "0.6.1"]]
  
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  
  :figwheel {:nrepl-port 7888
             :css-dirs ["resources/public"]}

  :sass {:src "resources/sass"
         :dst "resources/public"}
  
  :npm {:dependencies [[source-map-support "0.2.8"]
                       [express "4.13.3"]
                       [logfmt "1.2.0"]
                       [socket.io "1.3.7"]]
        :package {:scripts {:start "node dist/server/main.js"}}}
 
  :clean-targets ^{:protect false} ["resources/public/js" "target" "out" "dist"]
  
  :cljsbuild
  {:builds
   {:client-dev {:source-paths ["src/client"]
                 :figwheel true
                 :compiler {:main lambdaforge.core
                            :output-to "resources/public/js/bundle.js"
                            :asset-path "js/out"
                            :optimizations :none
                            :source-map true}}
    :server-dev {:source-paths ["src/server"]
                 :compiler {:main lambdaforge.core
                            :output-to "dist/server/main.js"
                            :output-dir "dist/server/out"
                            :target :nodejs
                            :cache-analysis true
                            :optimizations :none
                            :source-map true}}
    :server-test {:source-paths ["src/server" "test/server"]
                  :compiler {:output-to "dist/server/test/compiled.js"
                             :output-dir "dist/server/test"
                             :main lambdaforge.test-runner
                             :optimizations :none
                             :target :nodejs}}
    :server-prod {:source-paths ["src/server"]
                  :compiler {:main lambdaforge.core
                             :output-to "dist/server/bundle.js"
                             :target :nodejs
                             :cache-analysis true
                             :optimizations :advanced}}}})
