(ns Day04)

; Returns the digits of for a given natural number as a lazy sequence. For example:
; (digits 234789) => (9 8 7 4 3 2)
(defn digits [number]
  (map #(rem % 10) (take-while pos? (iterate #(quot % 10) number))))

; Returns the last digit of the given digits if the digits are in a valid order,
; i.e. never becoming smaller, or false if they do.
(defn is-valid [digits]
  (reduce #(if (> %2 %1) (reduced false) %2) digits))

; Returns a lazy sequence of the sizes of groups in a sequence of digits or nil if there are no groups.
; For example: (9 8 3 3 2 1 1 1) => (2 3)
(defn group-sizes [digits]
  (seq (filter (partial < 1) (vals (frequencies digits)))))

; Given a range of numbers, returns a lazy sequence of digit combinations
; that have both at least one group and are considered valid as defined by is-valid.
(defn possible-passwords [range]
  (filter group-sizes (filter is-valid (map digits range))))

; Parses a range from a raw string, i.e. "123-456" => (range 123 456)
(defn parse-range [raw]
  (apply range (map #(Integer/parseInt %) (.split #"-" raw))))

(def input (parse-range (slurp (first *command-line-args*))))

(let [passwords (possible-passwords input)
      narrowed-passwords (filter #(some (partial = 2) (group-sizes %)) passwords)]
  (println "Amount of possible passwords:" (count passwords))
  (println "Amount with extra condition:" (count narrowed-passwords)))