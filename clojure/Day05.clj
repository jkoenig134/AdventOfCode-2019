(load-file "intcode.clj")

(ns Day05
  (:require intcode))

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))

(println "Diagnostic program outputs (air conditioner):")
(println (last (:outputs (intcode/run input [2]))))

(println "Diagnostic program outputs (thermal radiators):")
(println (last (:outputs (intcode/run input [5]))))