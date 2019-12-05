(load-file "intcode.clj")

(ns Day05
  (:require intcode))

(slurp "F:/inf/AdventOfCode-2019/input/Day05.txt")
(def input (intcode/read-intcodes "F:/inf/AdventOfCode-2019/input/Day05.txt"))

(println "Outputs:")
(intcode/run input intcode/day5-opcode-map intcode/day5-param-modes)