(ns movile-turtle.core
  (:require [quil.core :as q]))

(def size 700)

(def ops (atom []))

(defmulti draw (fn [[x y angle] [op]] op))

(defn render []
  (q/background 0)
  (q/stroke (q/color 255))
  (q/stroke-weight 2)
  (reduce draw [(/ size 2) (/ size 2) (* -90 q/DEG-TO-RAD)] @ops))

(defn setup []
  (q/color-mode :hsb)
  (q/smooth)
  (q/frame-rate 20))

(q/defsketch display
  :title "Turtle Graphics"
  :setup setup
  :draw render
  :size [size size])

(defn show!
  "Renders a sequence of ops on screen. You can provide an optional delay between ops." 
  ([new-ops] (show! 10 new-ops))
  ([t new-ops]
    (reset! ops [])
    (doseq [op new-ops]
      (swap! ops conj op)
      (when (pos? t) (Thread/sleep t)))))

(defmethod draw :fwd [[x y angle] [_ d]]
  (let [x' (+ x (* d (Math/cos angle)))
        y' (+ y (* d (Math/sin angle)))]
    (q/line x y x' y')
    [x' y' angle]))

(defmethod draw :jump [[x y angle] [_ d]]
  (let [x' (+ x (* d (Math/cos angle)))
        y' (+ y (* d (Math/sin angle)))]
    [x' y' angle]))

(defmethod draw :left [[x y angle] [_ a]]
  [x y (- angle (* a q/DEG-TO-RAD))])

(defmethod draw :right [[x y angle] [_ a]]
  [x y (+ angle (* a q/DEG-TO-RAD))])

(defmethod draw :hue [state [_ h]]
  (q/stroke (q/color h 255 255))
  state)