module Main where

import Common

data Mode = Position | Immediate deriving Show

type Modes = [Mode]
type Operation = (Int, Modes)

-- Map an integer to a mode
getMode :: Int -> Mode
getMode 0 = Position
getMode 1 = Immediate

-- Get the value based on different modes
position :: Mode -> Int -> [Int] -> Int
position Position  idx list = list !! (list !! idx)
position Immediate idx list = list !! idx

-- Set the position based on different modes
setPos :: Mode -> [Int] -> Int -> Int -> [Int]
setPos Position  codes idx val = set codes (codes !! idx) val
setPos Immediate codes idx val = set codes idx val

-- Apply the add operation
add :: Modes -> [Int] -> Int -> [Int]
add (m:n:o:xs) codes offset = setPos o codes (3 + offset) ((pos m (1 + offset)) + (pos n (2 + offset)))
  where pos mode i = position mode i codes

-- Apply the multiplication operation
mult :: Modes -> [Int] -> Int -> [Int]
mult (m:n:o:xs) codes offset = setPos o codes (3+offset) ((pos m (1+offset)) * (pos n (2+offset)))
  where pos mode i = position mode i codes

-- Set a new value to the instruction pointer, if the value is true
jumpIfTrue :: Modes -> [Int] -> Int -> Int
jumpIfTrue (m:n:xs) codes offset = if ((pos m (1 + offset)) /= 0) then (pos n (2 + offset)) else (offset + 3)
  where pos mode i = position mode i codes

-- Set a new value to the instruction pointer, if the value is false
jumpIfFalse :: Modes -> [Int] -> Int -> Int
jumpIfFalse (m:n:xs) codes offset = if ((pos m (1 + offset)) == 0) then (pos n (2 + offset)) else (offset + 3)
  where pos mode i = position mode i codes

-- Set 1 if a < b otherwise 0
lessThan :: Modes -> [Int] -> Int -> [Int]
lessThan (m:n:o:xs) codes offset = setPos o codes (3+offset) (if (pos m (1+offset)) < (pos n (2+offset)) then 1 else 0)
  where pos mode i = position mode i codes

-- Set 1 if a = b otherwise 0
equals :: Modes -> [Int] -> Int -> [Int]
equals (m:n:o:xs) codes offset = setPos o codes (3+offset) ( if (pos m (1+offset)) == (pos n (2+offset)) then 1 else 0)
  where pos mode i = position mode i codes

-- Execute one atomar operation
operate :: Operation -> [Int] -> Int -> [Int]
operate (op, modes) codes offset
  | op == 1   = add modes codes offset
  | op == 2   = mult modes codes offset
  | op == 7   = lessThan modes codes offset
  | op == 8   = equals modes codes offset
  | op == 99  = codes
  | otherwise = error ("couldn't handle: " ++ (show op))

-- Execute the operations with a given input number and list of codes
run :: [Int] -> Int -> Int -> [Int] -> [Int]
run codes ip input output
  | op == 3   = run (set codes (codes !! (ip + 1)) input) (ip + 2) input output
  | op == 4   = run codes (ip + 2) input ((codes !! (codes !! (ip + 1))) : output)
  | op == 5   = run codes (jumpIfTrue modes codes ip) input output
  | op == 6   = run codes (jumpIfFalse modes codes ip) input output
  | op == 99  = output
  | otherwise = run (operate (parseCode (codes !! ip)) codes ip) (ip + 4) input output
  where
    op = fst (parseCode (codes !! ip))
    modes = snd (parseCode (codes !! ip))

-- Parse an opcode to get the actual op code and the parameter modes
parseCode :: Int -> Operation
parseCode code = (opcode, reverse $ map getMode (take 3 digits'))
  where
    digits' = digits code 5
    opcode  = (digits' !! 3) * 10 + (digits' !! 4)

-- Initialize run with zero IP and empty output
mainRun :: [Int] -> Int -> [Int]
mainRun codes input = run codes 0 input []
   
{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Line (Just line)) = head $ mainRun (toIntList line) 1

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Line (Just line)) = head $ mainRun (toIntList line) 5

-- Print challenge result
main = do
  solve "Air conditioner result"           (Line Nothing) (solve1)
  solve "Testing thermal radiators result" (Line Nothing) (solve2)