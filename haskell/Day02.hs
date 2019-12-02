module Main where

import Common

-- Execute the list of codes
execute :: [Int] -> Int -> [Int]
execute codes offset
  | (opcode == 99) = codes
  | (opcode == 1)  = execute add  (offset + 4)
  | (opcode == 2)  = execute mult (offset + 4)
  where
    add    = set codes (get 3) ((gget 1) + (gget 2))
    mult   = set codes (get 3) ((gget 1) * (gget 2))
    opcode = get 0
    get i  = codes !! (i + offset)
    gget i = codes !! (get i)

-- Set the value in a list by a given index
set :: [t] -> Int -> t -> [t]
set list idx val = (take idx list) ++ [val] ++ (drop (idx + 1) list)

-- Execute the codes with a given noun and verb
customExec :: [Int] -> Int -> Int -> Int
customExec codes noun verb = head $ execute (modify codes) 0
  where modify codes = set (set codes 1 noun) 2 verb

-- Force noun and verb to get 19690720
force codes = [(a,b) | a <- [0..99], b <- [0..99], (customExec codes a b) == 19690720]

{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Line (Just line)) = head $ execute (modify (toIntList line)) 0
  where modify codes = set (set codes 1 12) 2 2

-- Solve second challenge
solve2 :: Input -> [(Int, Int)]
solve2 (Line (Just line)) = force (toIntList line)

-- Print challenge result
main = do
  solve "First entry after execution" (Line Nothing) (solve1)
  solve "List of nouns and verbs" (Line Nothing) (solve2)