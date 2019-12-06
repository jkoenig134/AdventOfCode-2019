(load-file "intcode.clj")

(ns Day02
  (:require intcode))

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))

(println "Value at position 0 in alarm state:" (intcode/run (assoc input 1 12 2 2)))
(println "100 * noun + verb =" (intcode/find-fitting-input input 19690720 (range 100)))