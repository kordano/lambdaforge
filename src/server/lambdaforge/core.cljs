(ns lambdaforge.core
  (:require [cljs.nodejs :as node]))


(def InitialState (atom {}))
(def __dirname (js* "__dirname"))

(node/enable-util-print!)

(defn socket-handler
  "Dispatches incoming socket data"
  [socket]
  (.on socket "event" (fn [data] (println (str "Incoming data:" data))))
  (.on socket "disconnect" (fn [] (println "socket" socket "closed!"))))

(defn start-server
  "Starts server, initializes sockets"
  [state]
  (let [express (node/require "express")
        app (express)
        logfmt (node/require "logfmt")
        server (.createServer (node/require "http") app)
        io ((node/require "socket.io") server)]
    (.use app (.static express (str __dirname "dist/client")))
    (.get app "/" (fn [req res] (.send res "Welcome to Lambdaforge!")))
    (.on io "connection" socket-handler)
    (.listen server 8099)))

(defn -main
  "Entrypoint"
  []
  (let [port 8099]
    (start-server InitialState)
    (println (str "Server started on localhost:" port))))

(set! *main-cli-fn* -main)
