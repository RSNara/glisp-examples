(->> (-> 1 (* 2) (/ 3) (.toString)) (.log js/console))
