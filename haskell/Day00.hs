module Main where

import System.Environment

-- Read a file by it's name line by line
readLines :: FilePath -> IO [String]
readLines = fmap lines . readFile

-- Flatten an array of strings to a single string
solve :: [[Char]] -> [Char]
solve [] = ""
solve (y:[]) = y 
solve (x:xs) = x ++ " " ++ (solve xs)

-- Run the main with file input argument
main = do
  x <- getArgs
  lines <- readLines $ head x
  let result = solve lines
  putStr "Check if it is working..\n"
  print result
  return result