(load-file "intcode.clj")

(ns Day11
  (:require intcode))

(def right-turns {:up :right
                  :right :down
                  :down :left
                  :left :up})

(def left-turns (zipmap (vals right-turns) (keys right-turns)))

; Returns the new face after having turned around with the given input (either 90 degrees to the left or right)
(defn turn [face input]
  (if (= input 0)
    (left-turns face)
    (right-turns face)))

; Updates the given position by "walking" 1 step in the direction of the face.
(defn update-pos [position face]
  (condp = face
    :up (update position 1 inc)
    :right (update position 0 inc)
    :down (update position 1 dec)
    :left (update position 0 dec)))

; Runs the hull painting robot program with a process state, face, current position and
; a map of position -> color to denote the panels. Panels that aren't in the map are considered to be black.
(defn run-robot [state face position panels]
  (let [current-color (get panels position 0)
        new-state (intcode/continue state current-color)
        outputs (take-last 2 (:outputs new-state))
        new-color (first outputs)
        new-face (turn face (last outputs))]
    (if (:terminated new-state)
      panels
      (recur new-state new-face (update-pos position new-face) (assoc panels position new-color)))))

; Char representations for the color codes
(def color-chars {0 \. 1 \#})

; Visualizes the given panels obtained from run-robot by printing them to the console.
(defn visualize-panels [panels]
  (let [positions (keys panels)
        min-x (apply min (map first positions))
        max-x (apply max (map first positions))
        min-y (apply min (map last positions))
        max-y (apply max (map last positions))]
    (doseq [x (range min-x (inc max-x))]
      (doseq [y (range min-y (inc max-y))]
        (print (color-chars (get panels [x y] 0))))
      (println))))


(def input (intcode/parse-intcodes (slurp (first *command-line-args*))))
(println "Number of distinct panels painted:" (count (run-robot (intcode/initial-state input) :up [0 0] {})))
(println "Registration identifier (read from top to bottom and turned 90 degrees):")
(visualize-panels (run-robot (intcode/initial-state input) :up [0 0] {[0 0] 1}))

