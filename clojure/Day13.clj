(load-file "intcode.clj")

(ns Day13
  (:require intcode))

(def tile-types
  {0 :empty
   1 :wall
   2 :block
   3 :paddle
   4 :ball})

(defn add-tile [tiles tile]
  (let [x (nth tile 0)
        y (nth tile 1)
        type (nth tile 2)]
    (assoc tiles [x y] (get tile-types type type))))

(defn insert-tiles [tiles state]
  (reduce add-tile tiles (partition 3 (:outputs state))))

(defn load-game [code]
  (let [result (intcode/run code)]
    (insert-tiles {} result)))

(defn count-tiles [tiles tile-type]
  (count (filter #{tile-type} (vals tiles))))

(defn play-game [code]
  (loop [state (intcode/run (assoc code 0 2))
         tiles (insert-tiles {} state)]
    (if (:terminated state)
      (tiles [-1 0])
      (let [ball-position (first (filter #(= :ball (tiles %)) (keys tiles)))
            paddle-position (first (filter #(= :paddle (tiles %)) (keys tiles)))
            joystick-tilt (compare (ball-position 0) (paddle-position 0))]
        (recur (intcode/continue state joystick-tilt) (insert-tiles tiles state))))))

(def input (intcode/parse-intcodes (slurp "../input/Day13.txt")))
(println "Number of block tiles:" (count-tiles (load-game input) :block))
(println "Game score:" (play-game input))                   ; FIXME prints :empty