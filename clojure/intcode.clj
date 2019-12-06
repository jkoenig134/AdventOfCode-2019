; common ops for day 2 and 5
(ns intcode)

; The terminal opcode (terminates the process)
(def terminal-opcode
  {:operator (fn [process-state & _]
               (assoc process-state :terminated true))
   :params   0})

; Returns an opcode that takes *param-amount* parameters, applies the given function to them (excpet the last)
; and returns a new state with updated memory and pointer.
(defn memory-change-opcode [function param-amount]
  {:operator (fn [process-state & params]
               (update
                 (assoc-in process-state [:memory (last params)]
                           (apply function (map (:memory process-state) (drop-last params))))
                 :pointer (partial + param-amount 1)))
   :params   param-amount})

; Returns an opcode that takes *param-amount* parameters, applies the given function to them
; (presumably for side-effects) and returns a new state with an updated pointer.
(defn side-effect-opcode [function param-amount]
  {:operator (fn [process-state & param-addresses]
               (do
                 (apply function (map (:memory process-state) param-addresses))
                 (update process-state :pointer (partial + param-amount 1))))
   :params   param-amount})

; Returns an opcode that takes two parameters, applies the given function to the first one
; and sets the process's pointer to the second one if the former returns a truthful value.
; Otherwise updates the pointer as usual.
(defn goto-opcode [test-fn]
  {:operator (fn [process-state value-pointer pointer-adress]
               (if (test-fn ((:memory process-state) value-pointer))
                 (assoc process-state :pointer ((:memory process-state) pointer-adress))
                 (update process-state :pointer (partial + 3))))
   :params   2})

; A map of opcodes this computer currently supports
(def opcode-map
  {1  (memory-change-opcode + 3)
   2  (memory-change-opcode * 3)
   4  (side-effect-opcode println 1)
   3  (memory-change-opcode #(do (println "Please input a value: ") (Integer/parseInt (read-line))) 1)
   5  (goto-opcode (complement zero?))
   6  (goto-opcode zero?)
   7  (memory-change-opcode #(if (< %1 %2) 1 0) 3)
   8  (memory-change-opcode #(if (= %1 %2) 1 0) 3)
   99 terminal-opcode})

; A map of parameter modes this computer currently supports
(def param-modes
  {0 nth
   1 (comp last vector)})

; Resolves a raw opcode (including parameter modes) to an opcode from the given opcode-map
; and the parameter type ids.
(defn resolve-opcode [opcode]
  (let [opcode-id (rem opcode 100)
        digits (map #(rem % 10) (drop 2 (iterate #(quot % 10) opcode)))]
    (assoc (opcode-map opcode-id) :param-modes (map param-modes digits))))

; Processes a vector of memory values via the given algorithm.
; (if process is in state "terminated", return memory; else, resolve opcode at pointer using opcode-map,
; apply associated operator to the parameters in the next addresses resolved using parameter-types, additionally,
; fetch following address if opcode requires a destination address. Repeat with the state returned by the opcode operator).
(defn process [process-state]
  (if (:terminated process-state)
    (:memory process-state)
    (let [pointer (:pointer process-state)
          memory (:memory process-state)
          opcode (resolve-opcode (memory pointer))
          operator (:operator opcode)
          param-modes (:param-modes opcode)
          param-addresses (range (inc pointer) (+ pointer (:params opcode) 1))
          parameters (map #(%1 memory %2) param-modes param-addresses)]
      (recur (apply operator process-state parameters)))))

; Returns the first value in the memory returned by process when using the given memory as the initial memory
(defn run [memory]
  (first (process {:terminated false :pointer 0 :memory memory})))

; Returns 100 * noun + verb for the first noun and verb combination (with noun and verb both in input-range)
; for whom (process (assoc memory 1 noun 2 verb)) returns the given result (nil if no such combination could be found).
(defn find-fitting-input [memory result input-range]
  (first (for [noun input-range
               verb input-range
               :when (= result (run (assoc memory 1 noun 2 verb)))]
           (+ (* 100 noun) verb))))

; Splits the given string at comma and new line, parses the results to integers and returns that as a vector
(defn parse-intcodes [raw]
  (vec (map #(Integer/parseInt %) (.split #",|\n" raw))))