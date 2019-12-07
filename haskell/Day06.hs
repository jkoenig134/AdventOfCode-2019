module Main where

import Common

-- Edge with start and end vertex
data Edge t = Edge t t deriving (Show, Eq)

-- Count all (transitive) edges that ends in the end vertex
count :: (Eq t) => [Edge t] -> t -> Int
count edges end = helper edges edges end
  where
    helper total [] end = 0
    helper total ((Edge a b):xs) end
      | (b == end) =  1 + (helper total total a) + (helper total xs end)
      | otherwise = helper total xs end

{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Lines (Just lines)) = sum (map (count edges) targets)
  where
    targets = map (\(Edge a b) -> b) edges
    edges = map parseEdge lines
    parseEdge line = Edge ((split line ')') !! 0) ((split line ')') !! 1)

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Lines (Just lines)) = 5

-- Print challenge result
main = do
  solve "Number of (in-)direct orbits" (Lines Nothing) (solve1)
  solve "TODO" (Lines Nothing) (solve2)