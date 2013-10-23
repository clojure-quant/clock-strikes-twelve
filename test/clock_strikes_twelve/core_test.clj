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


; Portfolio Theory & Investment Management
; Richard Dobbins & Stephan F. Witt
; published 1983
;
; Two-asset Example
; pages 32-34
;
; The securities of companies A and B have the following expected returns and
; variance of returns.
;
;            means   variances
; ----------------------------
; Company A  0.10      0.0225
; Company A  0.80      0.0144
;
; In addition the expected correlation of returns between the two stocks is 0.20.
; The expected return and risk for a set of portfolios is calculated from equations (2.7)
; and (2.11) as follows:
;
; E(R) is expected return and V(R) is variance of portfolio
;
; (1) 
;    Q: Find allocation of funds (weights) for securities A and B for desired return of 0.10. 
;    A: Allocated distribution of 100% in securities A and 0% in securities B.
;  
; (2) 
;    Q: Find allocation of funds for (weights) securities A and B for desired return of 0.08. 
;    A: Allocated distribution of 0% in securities A and 100% in securities B.
;
; (3) 
;    Q: Find allocation of funds for (weights) securities A and B for desired return of 0.096. 
;    A: Allocated distribution of 80% in securities A and 20% in securities B.
;
; (4) 
;    Q: Find allocation of funds for (weights) securities A and B for desired return of 0.084. 
;    A: Allocated distribution of 20% in securities A and 80% in securities B.
;
; (5) 
;    Q: Find allocation of funds for (weights) securities A and B for desired return of 0.092. 
;    A: Allocated distribution of 60% in securities A and 40% in securities B.
;
; (6) 
;    Q: Find allocation of funds for (weights) securities A and B for desired return of 0.088. 
;    A: Allocated distribution of 40% in securities A and 60% in securities B.
;
; (7) 
;    Q: Find allocation of funds for (weights) securities A and B for desired return of 0.09. 
;    A: Allocated distribution of 50% in securities A and 50% in securities B.

(deftest Portfolio-Theory-&-Investment-Management-Problem-1
  (let [result (minimum-variance-portfolio [0.10, 0.08] [0.0225 0.0144] 0.10)]
    (is (= (round (first result) :precision 2) 1.0))
    (is (= (round (first (rest result)) :precision 2) 0.0))))

(deftest Portfolio-Theory-&-Investment-Management-Problem-2
  (let [result (minimum-variance-portfolio [0.10, 0.08] [0.0225 0.0144] 0.08)]
    (is (= (round (first result) :precision 2) 0.0))
    (is (= (round (first (rest result)) :precision 2) 1.0))))

(deftest Portfolio-Theory-&-Investment-Management-Problem-3
  (let [result (minimum-variance-portfolio [0.10, 0.08] [0.0225 0.0144] 0.096)]
    (is (= (round (first result) :precision 2) 0.8))
    (is (= (round (first (rest result)) :precision 2) 0.2))))

(deftest Portfolio-Theory-&-Investment-Management-Problem-4
  (let [result (minimum-variance-portfolio [0.10, 0.08] [0.0225 0.0144] 0.084)]
    (is (= (round (first result) :precision 2) 0.2))
    (is (= (round (first (rest result)) :precision 2) 0.8))))

(deftest Portfolio-Theory-&-Investment-Management-Problem-5
  (let [result (minimum-variance-portfolio [0.10, 0.08] [0.0225 0.0144] 0.092)]
    (is (= (round (first result) :precision 2) 0.6))
    (is (= (round (first (rest result)) :precision 2) 0.4))))

(deftest Portfolio-Theory-&-Investment-Management-Problem-6
  (let [result (minimum-variance-portfolio [0.10, 0.08] [0.0225 0.0144] 0.088)]
    (is (= (round (first result) :precision 2) 0.4))
    (is (= (round (first (rest result)) :precision 2) 0.6))))

(deftest Portfolio-Theory-&-Investment-Management-Problem-7
  (let [result (minimum-variance-portfolio [0.10, 0.08] [0.0225 0.0144] 0.09)]
    (is (= (round (first result) :precision 2) 0.5))
    (is (= (round (first (rest result)) :precision 2) 0.5))))


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
