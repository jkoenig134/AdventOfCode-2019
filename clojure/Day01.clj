(ns Day01)

(defn calc-fuel [mass]
  (- (quot mass 3) 2))

(defn calc-recursive-fuel [mass]
  (apply + (take-while pos? (drop 1 (iterate calc-fuel mass)))))

(defn simple-fuel [masses]
  (apply + (map calc-fuel masses)))

(defn recursive-fuel [masses]
  (apply + (map calc-recursive-fuel masses)))

(defn read-ints [file-path]
  (with-open [reader (clojure.java.io/reader file-path)]
    (doall (map #(Integer/parseInt %) (line-seq reader)))))

(def input (read-ints (first *command-line-args*)))

(println "Fuel required:" (simple-fuel input))
(println "Fuel required (including fuel for fuel):" (recursive-fuel input))