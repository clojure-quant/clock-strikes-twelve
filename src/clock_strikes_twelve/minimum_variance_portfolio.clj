(use 'incanter.core
     'incanter.matrix)

(defn minimum-variance-portfolio
  [mean variance return_level]
  (let [mean (trans expected-mean-of-returns)
        n    (count mean)
        a    (mmult (matrix 1 1 n) (solve variance) (mean))
        b    (mmult (trans mean) (solve variance) (mean))
        c    (mmult (matrix 1 1 n) (solve variance) (matrix 1 n 1))
        d    (det (matrix [[b a] [a c]]))
        e    (mmult (/ 1 d) (mmult (- (* b (matrix 1 n 1)) a) (trans mean) (solve variance)) 
        f    (mmult (/ 1 d) (mmult (- (* c (trans mean)) a) (matrix 1 1 n) (solve variance))]
  ((+ e (* f return_level)))        
)