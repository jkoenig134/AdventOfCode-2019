module Main where

import Common

-- 1 2 3 4 5 6

validate :: Int -> Bool
validate number = size && increase number 100000 False
  where
    size = number `div` 100000 >= 1 && number `div` 100000 <= 10
    increase a pos b
      | pos == 1 = b
      | otherwise = (a `div` pos) <= (a - (a `div` pos) * pos) `div` (pos `div` 10) && increase (a - (a `div` pos) * pos) (pos `div` 10) bool'
    
      where bool' = if b then b else ((a `div` pos) == (a - (a `div` pos) * pos) `div` (pos `div` 10))

run :: Int -> Int -> Int
run from to = helpRun from 0
  where
    helpRun current sum
      | current == to = sum
      | validate current = helpRun (current + 1) (sum + 1)
      |otherwise = helpRun (current + 1) sum
    
{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Lines (Just lines)) = 5

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Lines (Just lines)) = 5

-- Print challenge result
main = do
  solve "TODO" (Lines Nothing) (solve1)
  solve "TODO" (Lines Nothing) (solve2)