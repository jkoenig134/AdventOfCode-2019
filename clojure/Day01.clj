(ns Day01)

; Calculates the fuel for a given mass (m div 3 - 2)
(defn calc-fuel [mass]
  (- (quot mass 3) 2))

; Calculates the fuel for a given mass + the fuel for that fuel + the fuel for that fuel etc.. until 0 is reached.
; Returns the sum of all these.
(defn calc-recursive-fuel [mass]
  (apply + (take-while pos? (drop 1 (iterate calc-fuel mass)))))

; Returns the sum of the (simple) fuel values of the masses
(defn total-fuel-simple [masses]
  (apply + (map calc-fuel masses)))

; Returns the sum of the (recursive) fuel values of the masses
(defn total-fuel-recursive [masses]
  (apply + (map calc-recursive-fuel masses)))

; Reads and parses the lines of a file to ints. Returns them as a sequence (not lazy)
(defn read-ints [file-path]
  (with-open [reader (clojure.java.io/reader file-path)]
    (doall (map #(Integer/parseInt %) (line-seq reader)))))

(def input (read-ints (first *command-line-args*)))

(println "Fuel required:" (total-fuel-simple input))
(println "Fuel required (including fuel for fuel):" (total-fuel-recursive input))