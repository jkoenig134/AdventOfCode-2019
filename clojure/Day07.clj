(load-file "intcode.clj")

(ns Day07
  (:require intcode)
  (:import (clojure.lang PersistentQueue)))

; Returns a sequence of all possible permutations for a given collection.
(defn permutations [coll]
  (lazy-seq
    (if (seq (rest coll))
      (apply concat (for [element coll] (map #(cons element %) (permutations (remove #{element} coll)))))
      [coll])))

; Creates a sequence of the initial amp states, initialised with the Amp Controller Software memory and one of the given settings
(defn prepare-amps [acs settings]
  (map (partial intcode/initial-state acs) settings))

; Returns true if all of the given states are terminated
(defn all-terminated? [states]
  (not-any? (complement identity) (map :terminated states)))

; Runs a feedback loop given the Amp Controller Software and settings.
; It loops until all amp states are terminated, which means that it can be used for both parts.
(defn feedback-loop [acs settings]
  (loop [amp-states (reduce conj PersistentQueue/EMPTY (prepare-amps acs settings))
         input 0]
    (if (all-terminated? amp-states)
      input
      (let [processed (intcode/continue (first amp-states) input)]
        (recur (conj (pop amp-states) processed) (last (:outputs processed)))))))

; Finds the largest output signal possible given an acs and the possible phase settings.
(defn largest-output-signal [acs possible-settings]
  (apply max (for [settings (permutations possible-settings)]
               (feedback-loop acs settings))))

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))

(println "Highest output signal:" (largest-output-signal input (range 5)))
(println "Highest output signal (with feedback loop settings):" (largest-output-signal input (range 5 10)))