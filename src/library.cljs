(def defmacro
  (macro [name args & body]
    (Stack (quote def) name (.concat (Stack (quote macro) args) body))))

(defmacro defn [name args & body]
  (Stack (quote def) name (.concat (Stack (quote fn) args) body)))

(defn trace [x] (.log js/console x) x)
(defn js-trace [x] (.log js/console (.toJS x) x))

(defn first [[x]] x)
(defn rest [[x & others]] others)
(defn identity [x] x)
(def undefined ((fn [])))

(defn even? [x] (= 0 (mod x 2)))
(defn odd? [x] (! (= 0 (mod x 2))))

(defmacro when [test & body]
  (Stack (quote if) test
    (.concat (Stack (quote do)) body)))

(defmacro apply [func args]
  (.concat (Stack func) args))

(defmacro cond [& clauses]
  (let [count (.count clauses)]
    (if (odd? count)
      (throw (js/Error (.concat "cond: Expected there to be even number of args, but got " count)))
      (if (= 0 count)
        undefined
        (let [[test consequent & others] clauses]
          (Stack (quote if) test
            consequent
            (.concat (Stack (quote cond)) others)))))))

(defmacro case [expr & clauses]
  (let [count (.count clauses)]
    (if (= count 0)
      (quote (throw (js/Error (.concat "cons: No matching clause found for expression " (unquote expr)))))
      (if (= count 1)
        (first clauses)
        (let [[test consequent & others] clauses]
          (quote  (if (= (unquote test) (unquote expr))
                      (unquote consequent)
                      (unquote
                        (.concat (Stack (quote case) expr) others)))))))))

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
