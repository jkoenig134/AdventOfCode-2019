; common ops for day 2 and 5
(ns intcode)

; The terminal opcode (terminates the process)
(def terminal-opcode
  {:operator (fn [process-state & _]
               (assoc process-state :terminated true))
   :params   0})

; Returns an opcode that takes *param-amount* parameters, applies the given function to them (except the last)
; and returns a new state with updated memory and pointer.
(defn calc-opcode [function param-amount]
  {:operator (fn [process-state & param-addresses]
               (update
                 (assoc-in process-state [:memory (last param-addresses)]
                           (apply function (map (:memory process-state) (drop-last param-addresses))))
                 :pointer (partial + param-amount 1)))
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

; The input opcode. Takes the first of the remaining inputs in the state and stores it at the given address parameter.
; If no further input is available, interrupts the process.
(def input-opcode
  {:operator (fn [process-state destination]
               (if (seq (:inputs process-state))
                 (-> process-state
                     (assoc-in [:memory destination] (first (:inputs process-state)))
                     (update :inputs rest)
                     (update :pointer (partial + 2)))
                 (assoc process-state :interrupted true)))
   :params   1})

; The output opcode. Adds the parameter value to the outputs of the process state.
(def output-opcode
  {:operator (fn [process-state address]
               (-> process-state
                   (update :outputs #(conj % ((:memory process-state) address)))
                   (update :pointer (partial + 2))))
   :params   1})


; A map of opcodes this computer currently supports
(def opcode-map
  {1  (calc-opcode + 3)
   2  (calc-opcode * 3)
   3  input-opcode
   4  output-opcode
   5  (goto-opcode (complement zero?))
   6  (goto-opcode zero?)
   7  (calc-opcode #(if (< %1 %2) 1 0) 3)
   8  (calc-opcode #(if (= %1 %2) 1 0) 3)
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

; Processes a process-state via the given algorithm.
; (if process is in state "terminated", return the state; else, resolve opcode at pointer using opcode-map,
; apply associated operator to the parameters in the next addresses resolved using parameter-types.
; Repeat with the state returned by the opcode operator).
(defn process [process-state]
  (if (or (:interrupted process-state) (:terminated process-state))
    process-state
    (let [pointer (:pointer process-state)
          memory (:memory process-state)
          opcode (resolve-opcode (memory pointer))
          operator (:operator opcode)
          param-modes (:param-modes opcode)
          param-addresses (range (inc pointer) (+ pointer (:params opcode) 1))
          parameters (map #(%1 memory %2) param-modes param-addresses)]
      (recur (apply operator process-state parameters)))))

(defn initial-state [memory & inputs]
  {:pointer 0
   :memory memory
   :inputs (into [] inputs)
   :outputs []})

; Returns the final state when having processed the given intcode memory with the inputs.
(defn run [memory inputs]
  (process (initial-state memory inputs)))

; Splits the given string at comma and new line, parses the results to integers and returns that as a vector
(defn parse-intcodes [raw]
  (vec (map #(Integer/parseInt %) (.split #",|\n" raw))))