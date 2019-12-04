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
      | otherwise = helpRun (current + 1) sum

run2 :: Int -> Int -> Int
run2 from to = helpRun from 0
  where
    helpRun current sum
      | current == to = sum
      | validate2 current = helpRun (current + 1) (sum + 1)
      | otherwise = helpRun (current + 1) sum

validate2 :: Int -> Bool
validate2 number = validate number && contains (map (containsTwo digits') digits') True
  where digits' = digits number 6

digits :: Int -> Int -> [Int]
digits a 0 = []
digits a length = digit : digits (a - (digit * 10^ (length - 1))) (length - 1)
  where digit = a `div` (10 ^ (length - 1))

containsTwo :: (Eq t) => [t] -> t -> Bool
containsTwo list t = (helper list 0) == 2
  where
    helper [] i = i
    helper (x:xs) i = if x == t then helper xs (i + 1) else helper xs i

-- Split a string by a deliminator
split :: String -> Char -> [String]
split []   c = []
split list c = first : split (drop ((length first) + 1) list) c
  where first = takeWhile (/= c) list

contains :: (Eq t) => [t] -> t -> Bool
contains [] t = False
contains (x:xs) t = if x == t then True else contains xs t
    
{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Line (Just line)) = run (read (splitted !! 0)) (read (splitted !! 1))
  where splitted = split line '-'

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Line (Just line)) = run2 (read (splitted !! 0)) (read (splitted !! 1))
  where splitted = split line '-'

-- Print challenge result
main = do
  solve "Amount of different passwords" (Line Nothing) (solve1)
  solve "Amount of different passwords (2)" (Line Nothing) (solve2)