; common ops for day 2 and 5
(ns intcode)

; Returns a function to be used as an opcode-operator. takes *params* parameters,
; applies the given function to them and returns a new state with updated memory and pointer.
(defn memory-change-opcode [function params]
  {:operator    (fn [process-state destination & params]
                  (update
                    (assoc-in process-state [:memory destination] (apply function params))
                    :pointer (partial + (count params) 2)))
   :params      params
   :destination true})

; Resolves a raw opcode (including parameter type ids) to an opcode from the given opcode-map
; and the parameter type ids.
(defn resolve-opcode [opcode opcode-map]
  (let [opcode-id (rem opcode 100)
        digits (map #(rem % 10) (drop 2 (iterate #(quot % 10) opcode)))]
    (assoc (opcode-map opcode-id) :param-type-ids digits)))

; Processes a vector of memory values via the given algorithm.
; (if process is in state "terminated", return memory; else, resolve opcode at pointer using opcode-map,
; apply associated operator to the parameters in the next addresses resolved using parameter-types, additionally,
; fetch following address if opcode requires a destination address. Repeat with the state returned by the opcode operator).
(defn process [process-state opcode-map param-modes]
  (if (:terminated process-state)
    (:memory process-state)
    (let [pointer (:pointer process-state)
          memory (:memory process-state)
          opcode (resolve-opcode (memory pointer) opcode-map)
          operator (:operator opcode)
          params (:params opcode)
          parameters (map #((param-modes %1) memory %2) (:param-type-ids opcode) (range (inc pointer) (+ pointer params 1)))]
      (recur (apply operator process-state (if (:destination opcode) (conj parameters (memory (+ pointer params 1))) parameters))
             opcode-map param-modes))))

; Returns the first value in the memory returned by process when using the given memory as the initial memory
(defn run [memory opcode-map param-modes]
  (first (process {:terminated false :pointer 0 :memory memory} opcode-map param-modes)))

; Returns 100 * noun + verb for the first noun and verb combination (with noun and verb both in input-range)
; for whom (process (assoc memory 1 noun 2 verb)) returns the given result (nil if no such combination could be found).
(defn find-fitting-input [memory result input-range opcode-map param-modes]
  (first (for [noun input-range
               verb input-range
               :when (= result (run (assoc memory 1 noun 2 verb) opcode-map param-modes))]
           (+ (* 100 noun) verb))))

; Reads a file, splits at comma and new line, parses the results to integers and returns that as a vector
(defn read-intcodes [file-path]
  (vec (map #(Integer/parseInt %) (.split #",|\n" (slurp file-path)))))

; The parameter modes used in day 2
(def day2-param-modes
  {0 (fn [memory pointer] (memory (memory pointer)))})

; The opcodes used in day 2
(def day2-opcode-map
  {1  (memory-change-opcode + 2)
   2  (memory-change-opcode * 2)
   99 {:operator    (fn [process-state & _] (assoc process-state :terminated true))
       :params      0
       :destination false}})

; The opcodes used in day 5
(def day5-opcode-map
  (assoc day2-opcode-map
    3 (memory-change-opcode (constantly 1) 0)
    4 {:operator (fn [process-state param] (do (println param) process-state))
       :params 1
       :destination false}))

; The parameter modes used in day 2
(def day5-param-modes
  (assoc day2-param-modes 1 nth))