(ns Day06)

; Returns the number of orbits the given object is contained in (i.e. direct orbit + indirect orbits)
(defn orbit-centers [orbit-map object]
  (take-while some? (drop 1 (iterate orbit-map object))))

; Returns the sum of the orbit centers for each object in the orbit map.
(defn direct-and-indirect-orbits [orbit-map]
  (apply + (map count (map (partial orbit-centers orbit-map) (keys orbit-map)))))

; Counts the amount of elements in coll before element appears. Returns (count coll) if it doesn't appear at all.
(defn count-until [element coll]
  (count (take-while (partial not= element) coll)))

; Calculates the minimum amount of traversals required in an orbit map to move from object to destination
(defn traversals [orbit-map object destination]
  (let [object-centers (orbit-centers orbit-map object)
        destination-centers (orbit-centers orbit-map destination)
        first-common-center (some (set destination-centers) object-centers)]
    (+ (count-until first-common-center object-centers)
       (count-until first-common-center destination-centers))))

; Parses the input format into a map of object -> orbit center
(defn parse-orbit-map [raw]
  (apply hash-map (flatten (map (comp reverse #(.split #"\)" %)) (.split #"\n" raw)))))

(def input (parse-orbit-map (slurp (first *command-line-args*))))
(println "Total number of (in)direct orbits:" (direct-and-indirect-orbits input))
(println "Traversals required to get from you to santa:" (traversals input "YOU" "SAN"))