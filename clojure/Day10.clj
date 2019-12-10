(ns Day10)

(defn gcd [a b]
  (if (zero? b) a (recur b (mod a b))))

(defn vector-add [& vectors]
  (vec (reduce (partial map +) vectors)))

(defn points-between [a b]
  (let [y-diff (- (b 1) (a 1))
        x-diff (- (b 0) (a 0))
        gcd (Math/abs (gcd y-diff x-diff))]
    (if (> gcd 1)
      (let [direction [(/ x-diff gcd) (/ y-diff gcd)]]
        (take-while (partial not= b) (drop 1 (iterate (partial vector-add direction) a))))
      ())))

(defn can-view? [from to asteroids]
  (not-any? asteroids (points-between from to)))

(defn best-possible-vision [asteroids]
  (apply max (for [asteroid asteroids]
               (dec (count (filter #(can-view? asteroid % asteroids) asteroids))))))

(defn asteroid-positions [space-map]
  (set (for [y (range (count space-map)) x (range (count (space-map y)))
             :when (= \# (get-in space-map [y x]))]
         [x y])))

(defn parse-map [raw]
  (vec (map vec (.split #"\n" raw))))

(def input (asteroid-positions (parse-map (slurp (first *command-line-args*)))))
(println (best-possible-vision input))