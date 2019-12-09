(load-file "intcode.clj")

(ns Day09
  (:require intcode))

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))

(println "BOOST test keycode:" (first (:outputs (intcode/run input 1))))

