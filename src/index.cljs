(->> (-> 1.0M (* 2) (/ 3) (.toString)) (.log js/console))

(defn reducer [state, action]
  (let [type (aget action "type")]
    (case type
      "increment" (+ state 1)
      "decrement" (- state 1)
       state)))

(def store (.createStore Redux reducer 0))

(defn dispatch [action]
  (.dispatch store (.toObject action)))

(defn subscribe [func]
  (.subscribe store func))

(defn get-state []
  (.getState store))

(defn render [state]
  (.h VirtualDOM "div" (.toArray [(.concat "" state)])))

(def tree (render (get-state)))
(def root-node (.create VirtualDOM tree))

(def dom-state (.toObject {"tree" tree "root-node" root-node}))

(.appendChild (aget js/document "body") root-node)

(subscribe (fn []
              (.log js/console "Starting DOM update!")
              (let [state (get-state)
                    old-tree (aget dom-state "tree")
                    old-root-node (aget dom-state "root-node")
                    new-tree (render state)
                    patches (.diff VirtualDOM old-tree new-tree)
                    new-root-node (.patch VirtualDOM old-root-node patches)]
                (aset dom-state "root-node" new-root-node)
                (aset dom-state "tree" new-tree))
              (.log js/console "Finished DOM update!")))

(dispatch {"type" "increment"})
(dispatch {"type" "increment"})
(dispatch {"type" "increment"})
(dispatch {"type" "decrement"})
