(ns Day10)

; Determines the greatest common divisor of two numbers.
(defn gcd [a b]
  (if (zero? b) a (recur b (mod a b))))

; Adds the given vectors (mathematically)
(defn vector-add [& vectors]
  (vec (reduce (partial map +) vectors)))

; Creates the directional vector of a line that passes through "from" and "to".
; The vector is shortened as much as possible (so that it still hits full units).
(defn direction-vector [from to]
  (let [y-diff (- (to 1) (from 1))
        x-diff (- (to 0) (from 0))
        gcd (max 1 (Math/abs (gcd y-diff x-diff)))]
    [(/ x-diff gcd) (/ y-diff gcd)]))

; Returns an infinite sequence of all points starting from the point "from"
; and stepping in "direction" each iteration.
(defn ray [from direction]
  (iterate #(vector-add % direction) from))

; Returns all points with natural components between a and b, both exclusively.
(defn points-between [a b]
  (take-while (partial not= b) (drop 1 (ray a (direction-vector a b)))))

; Calculates the point product of two vectors.
(defn point-prod [vector1 vector2]
  (apply + (map * vector1 vector2)))

; Calculates the length or the absolute value of a vector.
(defn length [vector]
  (Math/sqrt (point-prod vector vector)))

; Calculates the angle between two lines, represented by their directional vector.
; Returns 0-180 degrees for positive x components and 180-360 degrees for negative x components.
; (This is used to distinguish between mathematically equivalent laser positions)
(defn angle [vector1 vector2]
  (let [angle (Math/acos (/ (point-prod vector1 vector2) (* (length vector1) (length vector2))))]
    (if (or (pos? (vector2 0)) (= vector1 vector2)) angle (- 180 angle))))

; Returns false if there are any asteroids between from and to directly, true otherwise.
(defn can-view? [from to asteroids]
  (not-any? asteroids (points-between from to)))

; Finds the asteroid in a set of asteroids that sees the most other asteroids
; and returns that number and its position as a point in a map.
(defn best-possible-vision [asteroids]
  (apply max-key :detectable-asteroids
         (for [asteroid asteroids]
           {:position             asteroid
            :detectable-asteroids (dec (count (filter #(can-view? asteroid % asteroids) asteroids)))})))

; Extracts the set of asteroid positions from a space map.
(defn asteroid-positions [space-map]
  (set (for [y (range (count space-map)) x (range (count (space-map y)))
             :when (= \# (get-in space-map [y x]))]
         [x y])))

; Parses a raw input string to a space map (a vector of vectors of characters).
(defn parse-map [raw]
  (vec (map vec (.split #"\n" raw))))

; Returns a sequence of all asteroid positions encountered when going from station in the given direction.
(defn laser-targets [asteroids station direction]
  (filter asteroids (take (count asteroids) (ray station direction))))

; Returns the sequence of asteroids that will be vaporized with a spinning laser as described in order.
(defn spinning-laser-targets [station asteroids]
  (let [laser-directions (sort-by (partial angle [0 -1]) (map #(direction-vector station %) asteroids))
        rays (distinct (map (partial laser-targets asteroids station) laser-directions))
        elements (count (max-key count rays))]
    (keep identity (apply interleave (map #(concat % (repeat (- elements (count %)) nil)) rays)))))

(def input (asteroid-positions (parse-map (slurp (first *command-line-args*)))))
(let [best (best-possible-vision input)]
  (println "Best possible asteroid position:" (:position best))
  (println "Number of detectable asteroids from that position:" (:detectable-asteroids best))
  (println)
  (println "Coordinates of the 200th asteroid to be evaporated by the laser (x * 100 + y):"
           (let [position (nth (spinning-laser-targets (:position best) (disj input (:position best))) 199)]
             (+ (* (position 0) 100) (position 1)))))