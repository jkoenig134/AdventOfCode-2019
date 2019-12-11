(ns Day10)

(defn gcd [a b]
  (if (zero? b) a (recur b (mod a b))))

(defn vector-add [& vectors]
  (vec (reduce (partial map +) vectors)))

(defn direction-vector [from to]
  (let [y-diff (- (to 1) (from 1))
        x-diff (- (to 0) (from 0))
        gcd (max 1 (Math/abs (gcd y-diff x-diff)))]
    [(/ x-diff gcd) (/ y-diff gcd)]))

(defn ray [from direction]
  (iterate #(vector-add % direction) from))

(defn points-between [a b]
  (take-while (partial not= b) (drop 1 (ray a (direction-vector a b)))))

(defn point-prod [vector1 vector2]
  (apply + (map * vector1 vector2)))

(defn length [vector]
  (Math/sqrt (point-prod vector vector)))

(defn angle [vector1 vector2]
  (let [angle (Math/acos (/ (point-prod vector1 vector2) (* (length vector1) (length vector2))))]
    (if (or (pos? (vector2 0)) (= vector1 vector2)) angle (- 180 angle))))

(defn can-view? [from to asteroids]
  (not-any? asteroids (points-between from to)))

(defn best-possible-vision [asteroids]
  (apply max-key :detectable-asteroids
         (for [asteroid asteroids]
           {:position             asteroid
            :detectable-asteroids (dec (count (filter #(can-view? asteroid % asteroids) asteroids)))})))

(defn asteroid-positions [space-map]
  (set (for [y (range (count space-map)) x (range (count (space-map y)))
             :when (= \# (get-in space-map [y x]))]
         [x y])))

(defn parse-map [raw]
  (vec (map vec (.split #"\n" raw))))

(defn laser-targets [asteroids station direction]
  (filter asteroids (take (count asteroids) (ray station direction))))

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