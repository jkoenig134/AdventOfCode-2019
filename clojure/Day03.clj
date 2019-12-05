(ns Day03
  (:require clojure.set))

; Returns the given position (a vector [x y]) "moved" 1 step in a direction, i.e. R, L, U, D
(defn update-pos [direction pos]
  (condp = direction
    \R (update pos 0 inc)
    \L (update pos 0 dec)
    \U (update pos 1 inc)
    \D (update pos 1 dec)))

; applies the given instruction (like "32R") to the given position by returning a sequence of
; all visited positions though that instruction paired with the corresponding total amount of
; steps required to get there
(defn advance [pos instruction total-steps]
  (let [direction (first instruction)
        steps (Integer/valueOf (subs instruction 1))]
    (take steps (drop 1 (iterate #(update (update % 0 (partial update-pos direction)) 1 inc) [pos total-steps])))))

; Returns a map of [x y] -> int, where the keys are the distinct positions the given wire-path
; (a list of instructions; see above) visits, and the values are the total amount of steps it takes the
; wire to get to the corresponding position.
(defn positions-visited
  ([instructions curr-pos visits steps]
   (if (empty? instructions)
     visits
     (let [next-visits (advance curr-pos (first instructions) steps)]
       (recur (rest instructions) ((last next-visits) 0)
              (apply merge visits (map (partial apply hash-map) (filter #(nil? (visits (% 0))) next-visits)))
              (+ steps (count next-visits))))))
  ([wire-path] (positions-visited wire-path [0 0] {} 0)))

; Calculates the taxicab/manhattan distance from [0 0] to pos
(defn distance-from-central-port [pos] (+ (Math/abs (pos 0)) (Math/abs (pos 1))))

; Returns a set of the positions where all of the given wires intersect
(defn intersections [wire-visits]
  (apply clojure.set/intersection (map #(set (keys %)) wire-visits)))

; Calculates the distance to the closest intersection for a given sequence of visit-maps
(defn distance-to-closest-intersection [wire-visits]
  (apply min (map distance-from-central-port (intersections wire-visits))))

; Helper function to map a sequence of visit-maps to the amount of steps it takes
; for each wire to get to the intersection position
(defn intersection-steps [wire-visits intersection]
  (map #(% intersection) wire-visits))

; Calculates the smallest possible sum of steps to get to a position where the wire-visits intersect
(defn smallest-sum-of-steps [wire-visits]
  (apply min (map (partial apply +) (map (partial intersection-steps wire-visits) (intersections wire-visits)))))

; Splits each line of the given string into instructions, i.e. returns a sequence of sequences of instructions for wires.
(defn parse-wire-paths [raw]
  (map #(.split #"," %) (map #(.trim %) (.split #"\n" raw))))

(def wire-visits (map positions-visited (parse-wire-paths (slurp (first *command-line-args*)))))

(println "Distance to closest intersection:" (distance-to-closest-intersection wire-visits))
(println "Smallest sum of steps to intersection:" (smallest-sum-of-steps wire-visits))