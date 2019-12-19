(ns Day12)

(defn vector-add [& vectors]
  (apply map + vectors))

(defn update-position [moon]
  (update moon :position vector-add (:velocity moon)))

(defn apply-gravity [moon other-moon]
  (update moon :velocity vector-add (map compare (:position other-moon) (:position moon))))

(defn step-time [moons]
  (doall (map update-position (map #(reduce apply-gravity % moons) moons))))

(defn simulate [moons]
  (iterate step-time moons))

(defn create-moon [position-strs]
  {:velocity [0 0 0]
   :position (map #(Integer/parseInt %) position-strs)})

(defn abs-vector [vector]
  (apply + (map #(Math/abs %) vector)))

(defn total-energy [moon]
  (* (abs-vector (:position moon)) (abs-vector (:velocity moon))))

(defn energy-in-system [moons]
  (apply + (map total-energy moons)))

(defn parse-moons [raw]
  (map create-moon (map #(re-seq #"-?\d+" %) (.split #"\n" raw))))

(def input (parse-moons (slurp (first *command-line-args*))))
(println "Total energy after 1000 simulation steps:" (energy-in-system (nth (simulate input) 1000)))