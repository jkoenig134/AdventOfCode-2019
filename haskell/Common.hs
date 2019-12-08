module Common where

import System.Environment

{- Input utility to read-in lines and convert them -}

-- Input type: Line or Lines
data Input = Line (Maybe String) | Lines (Maybe [String]) deriving (Eq)
  
-- Read a file by its name line by line
readLines :: FilePath -> IO [String]
readLines = fmap lines . readFile

-- Read a file by its name by a single line
readLine :: FilePath -> IO String
readLine = readFile

-- Convert a single line to a int list
toIntList :: String -> [Int]
toIntList parse = read ("[" ++ parse ++ "]")

-- Split a string by a deliminator
split :: String -> Char -> [String]
split []   c = []
split list c = first : split (drop ((length first) + 1) list) c
  where first = takeWhile (/= c) list

{- List utility to operate on lists without using Data.List -}

-- Set the value in a list by a given index
set :: [t] -> Int -> t -> [t]
set list idx val = (take idx list) ++ [val] ++ (drop (idx + 1) list)

-- Get the first index by value from list
idxByVal :: (Eq t) => [t] -> t -> Int
idxByVal []     val = error "value not found"
idxByVal (x:xs) val = search (x:xs) 0
  where search (x:xs) idx = if x == val then idx else search xs (idx + 1)

-- Count an element in the list
count :: (Eq t) => [t] -> t -> Int
count []     elem = 0
count (x:xs) elem = if (x == elem) then (1 + count xs elem) else (count xs elem)

{- Other utility -}

-- Get a list of digits from a number a
digits :: Int -> Int -> [Int]
digits a 0      = []
digits a length = digit : digits (a - (digit * 10 ^ (length - 1))) (length - 1)
  where digit = a `div` (10 ^ (length - 1))

{- Solve method to automate input parsing and result printing -}

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