(load-file "intcode.clj")

(ns Day05
  (:require intcode))

; The opcodes used in day 5
(def part1-opcode-map
  {1  (intcode/memory-change-opcode + 2)
   2  (intcode/memory-change-opcode * 2)
   3  (intcode/memory-change-opcode (constantly 1) 0)
   4  (intcode/side-effect-opcode println 1)
   99 intcode/terminal-opcode})

; The parameter modes used in day 2
(def param-modes
  {0 (fn [memory pointer] (memory (memory pointer)))
   1 nth})

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))

(println "Diagnostic program outputs (air conditioner):")
(intcode/run input part1-opcode-map param-modes)

(def part2-opcode-map
  (assoc part1-opcode-map
    3 (intcode/memory-change-opcode (constantly 5) 0)
    5 (intcode/goto-opcode (complement zero?))
    6 (intcode/goto-opcode zero?)
    7 (intcode/memory-change-opcode #(if (< %1 %2) 1 0) 2)
    8 (intcode/memory-change-opcode #(if (= %1 %2) 1 0) 2)))

(println "Diagnostic program outputs (thermal radiators):")
(intcode/run input part2-opcode-map param-modes)