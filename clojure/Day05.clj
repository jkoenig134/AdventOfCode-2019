(load-file "intcode.clj")

(ns Day05
  (:require intcode))

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))

(println "Diagnostic program outputs (air conditioner - enter 2 when asked for input):")
(intcode/run input)

(println "Diagnostic program outputs (thermal radiators - enter 5 when asked for input):")
(intcode/run input)