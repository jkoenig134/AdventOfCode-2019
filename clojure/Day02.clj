(load-file "incode.clj")

(ns Day02
  (:require intcode))

(def input (intcode/read-intcodes (first *command-line-args*)))

(println "Value at position 0 in alarm state:" (intcode/run (assoc input 1 12 2 2) intcode/day2-opcode-map intcode/day2-param-modes))
(println "100 * noun + verb =" (intcode/find-fitting-input input 19690720 (range 100) intcode/day2-opcode-map intcode/day2-param-modes))