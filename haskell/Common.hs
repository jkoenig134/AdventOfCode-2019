module Common where

import System.Environment

-- Input type: Line or Lines
data Input = Line (Maybe String) | Lines (Maybe [String]) deriving (Eq)
  
-- Read a file by its name line by line
readLines :: FilePath -> IO [String]
readLines = fmap lines . readFile

-- Read a file by its name by a single line
readLine :: FilePath -> IO String
readLine = readFile

-- Convert a list of string to a list of ints
toInt :: [[Char]] -> [Int]
toInt = map read

-- Run the solve method by providing a description and input type
solve :: (Show t) => String -> Input -> (Input -> t) -> IO ()
solve desc input solver = do
  args <- getArgs
  if (input == Line Nothing) then do
    line <- readLine $ head args
    let result = solver (Line (Just line))
    putStr (desc ++ " : " ++ (show result) ++ "\n")
  else if (input == Lines Nothing) then do
    lines <- readLines $ head args
    let result = solver (Lines (Just lines))
    putStr (desc ++ " : " ++ (show result) ++ "\n")
  else return()