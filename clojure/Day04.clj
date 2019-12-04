(ns Day04)

(defn digits [number]
  (map #(rem % 10) (iterate #(quot % 10) number)))

(defn is-valid [digits]
  (reduce #(if (> %2 %1) (reduced false) %2) digits))

(defn groups [digits]
  (seq (filter (partial < 1) (vals (frequencies digits)))))

(defn possible-passwords [range digit-amount]
  (filter groups (filter is-valid (map #(take digit-amount %) (map digits range)))))

(defn parse-range [raw]
  (apply range (map #(Integer/parseInt %) (.split #"-" raw))))

(def input (parse-range "183564-657474"))

(let [passwords (possible-passwords input 6)
      narrowed-passwords (filter (partial some (partial = 2)) (map groups passwords))]
  (println "Amount of possible passwords:" (count passwords))
  (println "Amount with extra condition:" (count narrowed-passwords)))