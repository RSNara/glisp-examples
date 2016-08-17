(def defmacro
  (macro [name args & body]
    (Stack (quote def) name (.concat (Stack (quote macro) args) body))))

(defmacro defn [name args & body]
  (Stack (quote def) name (.concat (Stack (quote fn) args) body)))

(defn trace [x] (.log js/console x) x)

(defmacro -> [init & forms]
  (let [ thread (fn [& [previous-form current]]
                  (if (Stack? current)
                    (let [[func & args] current]
                      (.concat (Stack func previous-form) args))
                    (Stack current previous-form)))]
    (.reduce forms thread init)))

(defmacro ->> [init & forms]
  (let [ thread (fn [& [previous-form current]]
                  (if (Stack? current)
                    (.concat current (Stack previous-form))
                    (Stack current previous-form)))]
    (.reduce forms thread init)))
