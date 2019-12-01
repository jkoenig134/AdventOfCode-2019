(ns Day01)

(defn calc-fuel [mass]
  (- (quot mass 3) 2))

(defn fuels [masses]
  (filter pos? (map calc-fuel masses)))

(defn simple-fuel [masses]
  (apply + (fuels masses)))

(defn recursive-fuel [masses]
  (apply + (apply concat (take-while seq (drop 1 (iterate fuels masses))))))

(defn read-ints [file-path]
  (with-open [reader (clojure.java.io/reader file-path)]
    (doall (map #(Integer/parseInt %) (line-seq reader)))))

(def input (read-ints (first *command-line-args*)))

(println "Fuel required:" (simple-fuel input))
(println "Fuel required (including fuel for fuel):" (recursive-fuel input))