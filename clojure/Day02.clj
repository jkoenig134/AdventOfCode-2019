(load-file "intcode.clj")

(ns Day02
  (:require intcode))

; The parameter modes used in day 2
(def day2-param-modes
  {0 (fn [memory pointer] (memory (memory pointer)))})

; The opcodes used in day 2
(def day2-opcode-map
  {1  (intcode/memory-change-opcode + 2)
   2  (intcode/memory-change-opcode * 2)
   99 intcode/terminal-opcode})

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))

(println "Value at position 0 in alarm state:" (intcode/run (assoc input 1 12 2 2) day2-opcode-map day2-param-modes))
(println "100 * noun + verb =" (intcode/find-fitting-input input 19690720 (range 100) day2-opcode-map day2-param-modes))