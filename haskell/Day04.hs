module Main where

import Common

-- Validate an integer by pattern (1)
validate :: Int -> Bool
validate number = size && increase number 100000 False
  where
    size = number `div` 100000 >= 1 && number `div` 100000 <= 10
    increase a pos b
      | pos == 1  = b
      | otherwise = (a `div` pos) <= (a - (a `div` pos) * pos) `div` (pos `div` 10) && increase (a - (a `div` pos) * pos) (pos `div` 10) bool'
    
      where bool' = if b then b else ((a `div` pos) == (a - (a `div` pos) * pos) `div` (pos `div` 10))

-- Validate an integer by pattern (2)
validate2 :: Int -> Bool
validate2 number = validate number && elem True (map (containsTwo digits') digits')
  where digits' = digits number 6

-- Run the validation function between a given range
run :: Int -> Int -> (Int -> Bool) -> Int
run from to func = length (filter func [from..to])

-- Get the list of digits from a number a
digits :: Int -> Int -> [Int]
digits a 0 = []
digits a length = digit : digits (a - (digit * 10^ (length - 1))) (length - 1)
  where digit = a `div` (10 ^ (length - 1))

-- Check if a list contains 2 times the same element
containsTwo :: (Eq t) => [t] -> t -> Bool
containsTwo list t = (helper list 0) == 2
  where
    helper [] i     = i
    helper (x:xs) i = if x == t then helper xs (i + 1) else helper xs i
    
{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Line (Just line)) = run (read (splitted !! 0)) (read (splitted !! 1)) validate
  where splitted = split line '-'

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Line (Just line)) = run (read (splitted !! 0)) (read (splitted !! 1)) validate2
  where splitted = split line '-'

-- Print challenge result
main = do
  solve "Amount of different passwords"     (Line Nothing) (solve1)
  solve "Amount of different passwords (2)" (Line Nothing) (solve2)