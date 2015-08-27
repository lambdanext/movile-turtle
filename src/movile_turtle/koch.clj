(ns movile-turtle.koch)

(defn koch-step-1
  [step]
  (case step
    :l [:l]
    :r [:r]
    :s [:s :l :s :r :s :l :s]))

(defn koch-step
  [steps]
  (mapcat koch-step-1 steps))

(defn to-logo
  [step & {:keys [fwd] :or {fwd 10}}]
  (case step
    :s [:fwd 10]
    :l [:left 60]
    :r [:right 120]))
