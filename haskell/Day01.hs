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
solve list = sum (map (\x -> x `div` 3 - 2) list)

-- Run the main with file input argument
main = do
  x <- getArgs
  lines <- readLines $ head x
  let result = solve $ toInt lines
  putStr "Sum of fuel requirements is "
  print result
  return result