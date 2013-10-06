(ns clock-strikes-twelve.core)

(use '(incanter core))

(defn ones
  [rows columns]
  (vec (repeat rows (vec (repeat columns 1)))))

(defn minimum-variance-portfolio
  [mean-of-returns variance-of-returns return_level]
  (let [mean      (matrix mean-of-returns)
        variances (diag variance-of-returns)
        n         (count mean)
        a         (sel (mmult (matrix (ones 1 n)) (solve variances) mean) :rows 0 :cols 0)
        b         (sel (mmult (trans mean) (solve variances) mean) :rows 0 :cols 0)
        c         (sel (mmult (matrix (ones 1 n)) (solve variances) (matrix (ones n 1))) :rows 0 :cols 0)
        d         (det (matrix [[b a] [a c]]))
        e         (mmult (mult (/ 1 d) (minus (mult b (matrix (ones 1 n))) (mult a (trans mean)))) (solve variances)) 
        f         (mmult (mult (/ 1 d) (minus (mult c (trans mean)) (mult a (ones 1 n)))) (solve variances))] 
  (plus e (mult f return_level))))

