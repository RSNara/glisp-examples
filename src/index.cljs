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

(subscribe (fn [] (.log js/console "Updating state!" (.toString (get-state)))))

(dispatch {"type" "increment"})
(dispatch {"type" "increment"})
(dispatch {"type" "increment"})
(dispatch {"type" "decrement"})
