(load-file "intcode.clj")

(ns Day07
  (:require intcode))

(defn permutations [coll]
  (if (seq (rest coll))
    (apply concat (for [element coll] (map #(cons element %) (permutations (remove #{element} coll)))))
    [coll]))

(defn amplify [amplifier input setting]
  (last (:outputs (intcode/run amplifier [setting input]))))

(defn run-amplifier [amplifier settings]
  (reduce (partial amplify amplifier) 0 settings))

(defn largest-output-signal [amplifier possible-settings]
  (apply max (for [settings (permutations possible-settings)]
               (run-amplifier amplifier settings))))

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))

(println "Highest output signal:" (largest-output-signal input (range 5)))