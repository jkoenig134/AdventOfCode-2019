(ns Day08)

; For an encoded image (sequence of chars or string) and dimensions in the format [x y],
; finds the layer that has the least '0' pixels and calculates the value required to solve part 1.
(defn test-corrupted [image dimensions]
  (let [layer (apply min-key #(% \0) (map frequencies (partition (apply * dimensions) image)))]
    (* (layer \1) (layer \2))))

; Layers a pixel onto a base pixel. If the new pixel is transparent, returns the base pixel, else the new pixel.
(defn layer [base-pixel new-pixel]
  (if (= \2 new-pixel) base-pixel new-pixel))

; Returns a suited string representation for an encoded pixel.
(defn pixel-to-str [pixel]
  (if (= pixel \0) " " "█"))

; Decodes an encoded image (raw string or sequence of chars).
; Splits the input into layers, reduces them by layering them on top of each other,
; maps each pixel to an according string representation, partitions the result into rows
; (depicted by the provided dimensions) and returns them as a string joined with new lines.
(defn decode [image dimensions]
  (apply str (map (partial apply str) (interpose "\n" (partition (dimensions 0) (map pixel-to-str (reduce (partial map layer) (reverse (partition (apply * dimensions) image)))))))))

(def input (slurp (first *command-line-args*)))

(println "Image corruption-test result:" (test-corrupted input [25 6]))
(println "Decoded message:")
(println (decode input [25 6]))