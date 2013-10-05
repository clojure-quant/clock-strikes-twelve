(ns clock-strikes-twelve.core)

(use '(incanter core))

(defn minimum-variance-portfolio
  [mean-of-returns variance-of-returns return_level]
  (let [mean      (trans mean-of-returns)
        variances (diag variance-of-returns)
        n         (count mean)
        a         (mmult (matrix [1 1 n]) (solve variances) (mean))
        b         (mmult (trans mean) (solve variances) (mean))
        c         (mmult (matrix [1 1 n]) (solve variances) (matrix 1 n 1))
        d         (det (matrix [[b a] [a c]]))
        e         (mmult (/ 1 d) (mmult (- (* b (matrix 1 n 1)) a) (trans mean) (solve variances))) 
        f         (mmult (/ 1 d) (mmult (- (* c (trans mean)) a) (matrix 1 1 n) (solve variances)))]
  (+ e (* f return_level))))

