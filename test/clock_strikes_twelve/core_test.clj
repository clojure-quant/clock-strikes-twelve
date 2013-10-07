(ns clock-strikes-twelve.core-test
  (:require [clojure.test :refer :all]
            [clock-strikes-twelve.core :refer :all]))

(use '(incanter core))

(defn round
  [x & {p :precision}]
  (if p
    (let [scale (Math/pow 10 p)]
      (-> x (* scale) Math/round (/ scale)))
    (Math/round x)))

; Introduction to Mathematical Programming Applications and Algorithms 2nd Ed
; Wayne L. Winston
; Duxbury Press 1995
;
; Example 33 pages 734-735
;
; An investor can invest in three assets with expected returns and variances as specified in the following table.
; t Asset E[r]  st.dev.(r)
; 1       10.0%   0.20
; 2       11.5%   0.10
; 3        8.0%   0.15
; The three assets are independent (uncorrelated).
;
; 1. What are the weights of the minimum variance portfolio with mean 9%?
;
; Solution: allocacted weight to each asset
;     Asset 1 Asset 2 Asset 3
; w = 0.21277 0.19149 0.59574

(deftest Intro-to-Math-Prog-App-Alg-test
  (let [result (minimum-variance-portfolio [0.10 0.11 0.08] [0.20 0.10 0.15] 0.09)]
    (is (= (round (first result) :precision 5) 0.21277))
    (is (= (round (first (rest result)) :precision 5) 0.19149))
    (is (= (round (first (rest (rest result))) :precision 5) 0.59574))))

; Financial Numerical Recipes in C++
; Bernt Arne Ødegaard
; book can be found at http://finance.bi.no/~bernt/gcc_prog/recipes/recipes.pdf
;
; Example problem pages 66-67
;
; I have 1000 to invest in three stocks. Let Si be the random variable representing
; the annual return on 1 invested in stock i. Thus, if Si = 0.12, 1 invested in stock
; i at the beginning of a year was worth 1.12 at the end of the year. We are given the
; following information: E(S1) = 0.14, E(S2) = 0.11, E(S3) = 0.10, var S1 = 0.20,
; var S2 = 0.08, var S3 = 0.18. Formulate a solution that can be used to find the portfolio
; that attains an expected annual returnof at least 12% and minimizes the variance of the
; annual dollar return on the portfolio.
;
; Solution:
; x1 = 0.38095, x2 = 0.47619, x3 = 0.14286
; The standard deviation is 274.30

(deftest Financial-Numerical-Recipes-test
  (let [result (minimum-variance-portfolio [0.14 0.11 0.10] [0.20 0.08 0.18] 0.12)]
    (is (= (round (first result) :precision 5) 0.38065))
    (is (= (round (first (rest result)) :precision 5) 0.47742))
    (is (= (round (first (rest (rest result))) :precision 5) 0.14194))))

; Mathematics for Finance: An Introduction to Financial Engineering
; Capinski, Marek
; Springer undergraduate mathematics series
;
; Example 5.10 pages 110-111
;
; (3 securities) Consider three securities with expected returns, standard deviations
; of returns
; µ1 = 0.10, s1 = 0.28 -> var1 = s1^2 = 0.28^2 = 0.0784
; µ2 = 0.15, s2 = 0.24 -> var1 = s1^2 = 0.24^2 = 0.0576
; µ3 = 0.20, s3 = 0.25 -> var1 = s1^2 = 0.25^2 = 0.0625
;
; Solution:
; asset allocation weights = [0.316 0.439 0.245]
;
; The expected return and standard deviation of this portfolio are
; µ = 0.146, sV = 0.162.

(deftest Introduction-to-Financial-Engineering-test
  (let [result (minimum-variance-portfolio [0.10 0.15 0.20] [0.0784 0.0576 0.0625] 0.146)]
    (is (= (round (first result) :precision 3) 0.349))
    (is (= (round (first (rest result)) :precision 3) 0.383))
    (is (= (round (first (rest (rest result))) :precision 3) 0.269))))
