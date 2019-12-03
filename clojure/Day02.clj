(ns Day02)

(def default-opcode-map
  {1 {:operator + :params 2}
   2 {:operator * :params 2}})

; Processes a vector of memory values via the given algorithm.
; (fetch opcode at pointer; if = 99, terminate; else,
; apply associated operator to the values in the addresses pointer - pointer + amount of parameters
; and store the result in the address given in the next address, repeat with pointer to next opcode)
(defn process
  ([memory pointer opcode-map]
   (let [opcode (memory pointer)]
     (if (= 99 opcode)
       memory
       (let [opcode-details (opcode-map opcode)
             next-opcode-pos (+ pointer (:params opcode-details) 2)
             operator (:operator opcode-details)
             parameters (map #(memory (memory %)) (range (inc pointer) (dec next-opcode-pos)))
             destination (memory (dec next-opcode-pos))]
         (recur (assoc memory destination (apply operator parameters)) next-opcode-pos opcode-map)))))
  ([memory] (first (process memory 0 default-opcode-map))))

; Returns 100 * noun + verb for the first noun and verb combination (with noun and verb both in input-range)
; for whom (process (assoc memory 1 noun 2 verb)) returns the given result (nil if no such combination could be found).
(defn find-fitting-input [memory result input-range]
  (first (for [noun input-range
               verb input-range
               :when (= result (process (assoc memory 1 noun 2 verb)))]
           (+ (* 100 noun) verb))))

; Reads a file, splits at comma and new line, parses the results to integers and returns that as a vector
(defn read-intcodes [file-path]
  (vec (map #(Integer/parseInt %) (.split #",|\n" (slurp file-path)))))

(def input (read-intcodes (first *command-line-args*)))

(println "Value at position 0 in alarm state:" (process (assoc input 1 12 2 2)))
(println "100 * noun + verb =" (find-fitting-input input 19690720 (range 100)))