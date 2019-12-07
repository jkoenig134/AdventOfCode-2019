(load-file "intcode.clj")

(ns Day02
  (:require intcode))

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))

; Returns 100 * noun + verb for the first noun and verb combination (with noun and verb both in input-range)
; for whom (process (assoc memory 1 noun 2 verb)) returns the given result (nil if no such combination could be found).
(defn find-fitting-input [memory result input-range]
  (first (for [noun input-range
               verb input-range
               :when (= result (intcode/run (assoc memory 1 noun 2 verb)))]
           (+ (* 100 noun) verb))))

(println "Value at position 0 in alarm state:" (intcode/run (assoc input 1 12 2 2)))
(println "100 * noun + verb =" (find-fitting-input input 19690720 (range 100)))