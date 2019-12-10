; common ops for day 2 and 5
(ns intcode
  (:import (clojure.lang BigInt)))

; Reads a value from memory
(defn read-mem [process-state address]
  (let [mem (:memory process-state)]
    (if (>= address (count mem)) 0 (mem address))))

; Writes a value to memory and returns the new process state
(defn write-mem [process-state address value]
  (let [mem (:memory process-state)]
    (if (>= address (count mem))
      (assoc process-state :memory (conj (into mem (repeat (- address (count mem)) 0)) value))
      (assoc-in process-state [:memory address] value))))

; The terminal opcode (terminates the process)
(def terminal-opcode
  {:operator (fn [process-state & _]
               (assoc process-state :terminated true))
   :params   0})

; Returns an opcode that takes *param-amount* parameters, applies the given function to them (except the last)
; and returns a new state with updated memory and pointer.
(defn calc-opcode [function param-amount]
  {:operator (fn [process-state & param-addresses]
               (-> process-state
                   (write-mem (last param-addresses) (apply function (map (partial read-mem process-state) (butlast param-addresses))))
                   (update :pointer + param-amount 1)))
   :params   param-amount})

; Returns an opcode that takes two parameters, applies the given function to the first one
; and sets the process's pointer to the second one if the former returns a truthful value.
; Otherwise updates the pointer as usual.
(defn goto-opcode [test-fn]
  {:operator (fn [process-state value-pointer destination]
               (if (test-fn (read-mem process-state value-pointer))
                 (assoc process-state :pointer (read-mem process-state destination))
                 (update process-state :pointer + 3)))
   :params   2})

; The input opcode. Takes the first of the remaining inputs in the state and stores it at the given address parameter.
; If no further input is available, interrupts the process.
(def input-opcode
  {:operator (fn [process-state destination]
               (if (seq (:inputs process-state))
                 (-> process-state
                     (write-mem destination (first (:inputs process-state)))
                     (update :inputs rest)
                     (update :pointer + 2))
                 (assoc process-state :interrupted true)))
   :params   1})

; The output opcode. Adds the parameter value to the outputs of the process state.
(def output-opcode
  {:operator (fn [process-state address]
               (-> process-state
                   (update :outputs conj (read-mem process-state address))
                   (update :pointer + 2)))
   :params   1})

(def rel-base-offset-opcode
  {:operator (fn [process-state offset-pointer]
               (-> process-state
                   (update :rel-base + (read-mem process-state offset-pointer))
                   (update :pointer + 2)))
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
   9  rel-base-offset-opcode
   99 terminal-opcode})

; A map of parameter modes this computer currently supports
(def param-modes
  {0 (fn [process-state address] (read-mem process-state address))
   1 (fn [_ address] address)
   2 (fn [process-state address]
       (+ (read-mem process-state address) (:rel-base process-state)))})

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
          parameters (map #(%1 process-state %2) param-modes param-addresses)]
      (recur (apply operator process-state parameters)))))

(defn initial-state [memory & inputs]
  {:pointer 0
   :memory memory
   :inputs (into [] inputs)
   :outputs []
   :rel-base 0})

; Returns the final state when having processed the given intcode memory with the inputs.
(defn run [memory & inputs]
  (process (apply initial-state memory inputs)))

; Splits the given string at comma and new line, parses the results to BigInts and returns that as a vector
(defn parse-intcodes [raw]
  (vec (map #(Long/parseLong %) (.split #",|\n" raw))))