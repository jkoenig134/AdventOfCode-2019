(load-file "intcode.clj")

(ns Day13
  (:require intcode))

; Adds or updates a tile in the provided map "tiles" given the 3-size output sequence in the format of (x y tile-type)
(defn assoc-tile [tiles tile-output]
  (let [x (nth tile-output 0)
        y (nth tile-output 1)
        type (nth tile-output 2)]
    (assoc tiles [x y] type)))

; Takes all outputs from a process state and updates the given map of tiles with them.
(defn update-tiles [tiles state]
  (reduce assoc-tile tiles (partition 3 (:outputs state))))

; Loads the game with no quarter provided and returns a map of all tiles that are created.
(defn load-game [code]
  (let [result (intcode/run code)]
    (update-tiles {} result)))

; Counts the amount of tiles of the given type in a tiles map
(defn count-tiles [tiles tile-type]
  (count (filter #{tile-type} (vals tiles))))

; In a map of tiles, finds the first key that is associated with the given tile type.
(defn find-tile-position [tiles tile-type]
  (first (filter #(= tile-type (tiles %)) (keys tiles))))

; Plays and beats the game, returns the score at the end.
(defn play-game [code]
  (loop [state (intcode/run (assoc code 0 2))
         tiles (update-tiles {} state)]
    (if (:terminated state)
      (tiles [-1 0])
      (let [ball-position (find-tile-position tiles 4)
            paddle-position (find-tile-position tiles 3)
            joystick-tilt (compare (ball-position 0) (paddle-position 0))
            next-state (intcode/continue state joystick-tilt)]
        (recur next-state (update-tiles tiles next-state))))))

(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))
(println "Number of block tiles:" (count-tiles (load-game input) 2))
(println "Game score:" (play-game input))