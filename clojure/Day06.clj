(ns Day06)

; Returns the number of orbits the given object is contained in (i.e. direct orbit + indirect orbits)
(defn orbit-centers [orbit-map object]
  (count (take-while some? (drop 1 (iterate orbit-map object)))))

; Returns the sum of the orbit centers for each object in the orbit map.
(defn direct-and-indirect-orbits [orbit-map]
  (apply + (map (partial orbit-centers orbit-map) (keys orbit-map))))

; Returns a sequence of objects that orbit the given center
(defn orbiting [orbit-map center]
  (map first (filter #(= (% 1) center) orbit-map)))

; Calculates the minimum amount of traversals required in an orbit map to move from object to destination
; (This is currently very inefficient and bad, I might still improve it)
(defn traversals [orbit-map object destination]
  (condp = object
    nil Integer/MIN_VALUE
    destination -2
    (inc (apply max (traversals (dissoc orbit-map object) (orbit-map object) destination)
                (for [orbit-obj (orbiting (dissoc orbit-map object) object)]
                  (traversals (dissoc orbit-map orbit-obj) orbit-obj destination))))))

; Parses the input format into a map of object -> orbit center
(defn parse-orbit-map [raw]
  (apply hash-map (flatten (map (comp reverse #(.split #"\)" %)) (.split #"\n" raw)))))

(def input (parse-orbit-map (slurp (first *command-line-args*))))
(println "Total number of (in)direct orbits:" (direct-and-indirect-orbits input))
(println "Traversals required to get from you to santa:" (traversals input "YOU" "SAN"))