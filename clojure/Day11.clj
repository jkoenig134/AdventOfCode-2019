(load-file "intcode.clj")

(ns Day11
  (:require intcode))

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))
