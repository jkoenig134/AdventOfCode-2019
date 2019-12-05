module Main where

import Common

data Mode = Position | Immediate deriving Show

type Modes = [Mode]
type Operation = (Int, Modes)

getMode :: Int -> Mode
getMode 0 = Position
getMode 1 = Immediate

position :: Mode -> Int -> [Int] -> Int
position Position  idx list  = list !! (list !! idx)
position Immediate idx list = list !! idx

setPos :: Mode -> [Int] -> Int -> Int -> [Int]
setPos Position codes idx val = set codes (codes !! idx) val
setPos Immediate codes idx val = set codes idx val

add :: Modes -> [Int] -> Int -> [Int]
add (m:n:o:xs) codes offset = setPos o codes (3+offset) ((pos m (1+offset)) + (pos n (2+offset)))
  where pos mode i = position mode i codes

mult :: Modes -> [Int] -> Int -> [Int]
mult (m:n:o:xs) codes offset = setPos o codes (3+offset) ((pos m (1+offset)) * (pos n (2+offset)))
  where pos mode i = position mode i codes

operate :: Operation -> [Int] -> Int -> [Int]
operate (op, modes) codes offset
  | op == 1 = add modes codes offset
  | op == 2 = mult modes codes offset
  | op == 99 = codes
  | otherwise = error ("couldn't handle: " ++ (show op))
  
run :: [Int] -> Int -> Int -> [Int] -> [Int]
run codes ip input output
  | op == 3 = run (set codes (codes !! (ip + 1)) input) (ip + 2) input output
  | op == 4 = run codes (ip + 2) input ((codes !! (codes !! (ip + 1))) : output)
  | op == 99 = output
  | otherwise = run (operate (parseCode (codes !! ip)) codes ip) (ip + 4) input output
  where op = fst (parseCode (codes !! ip))

mainRun :: [Int] -> Int -> [Int]
mainRun codes input = run codes 0 input []

parseCode :: Int -> Operation
parseCode code = (opcode, reverse $ map getMode (take 3 digits'))
  where
    digits' = digits code 5
    opcode = (digits' !! 3) * 10 + (digits' !! 4)


-- Get digits
digits :: Int -> Int -> [Int]
digits a 0 = []
digits a length = digit : digits (a - (digit * 10^ (length - 1))) (length - 1)
  where digit = a `div` (10 ^ (length - 1))
   
-- Set the value in a list by a given index
set :: [t] -> Int -> t -> [t]
set list idx val = (take idx list) ++ [val] ++ (drop (idx + 1) list)
   
{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> [Int]
solve1 (Line (Just line)) = mainRun (toIntList line) 1

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Line (Just line)) = 5

-- Print challenge result
main = do
  solve "BlessRNG"     (Line Nothing) (solve1)
  solve "TODO" (Line Nothing) (solve2)