module Main where

import Common

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

-- Split a string by a deliminator
split :: String -> Char -> [String]
split []   c = []
split list c = first : split (drop ((length first) + 1) list) c
  where first = takeWhile (/= c) list
    
{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Line (Just line)) = run (read (splitted !! 0)) (read (splitted !! 1))
  where splitted = split line '-'

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Lines (Just lines)) = 5

-- Print challenge result
main = do
  solve "Amount of different passwords" (Line Nothing) (solve1)
  solve "TODO" (Lines Nothing) (solve2)