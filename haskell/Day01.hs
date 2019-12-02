module Main where

import System.Environment

-- Read a file by it's name line by line
readLines :: FilePath -> IO [String]
readLines = fmap lines . readFile

-- Convert a list of string to a list of ints
toInt :: [[Char]] -> [Int]
toInt = map read

-- Solve the problem by simple calculations
solve :: [Int] -> Int
solve list = sum (map calculateFuel list)

solve2 :: [Int] -> Int
solve2 list = sum (map calculateExtendendFuel list)

calculateExtendendFuel :: Int -> Int
calculateExtendendFuel mass = if fuel <= 0 then 0 else fuel + (calculateExtendendFuel fuel)
  where
    fuel = calculateFuel mass

-- Calculate the fuel by mass
calculateFuel :: Int -> Int
calculateFuel mass = mass `div` 3 - 2

-- Run the main with file input argument
main = do
  x <- getArgs
  lines <- readLines $ head x
  let result = solve $ toInt lines
  putStr "Sum of fuel requirements is "
  print result
  let result2 = solve2 $ toInt lines
  putStr "Extended sum of fuel requirements is "
  print result2
  return result