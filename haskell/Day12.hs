module Main where

import Common

type Point = [Int]
type Velocity = [Int]  
  
calculate :: [(Point, Velocity)] -> [(Point, Velocity)] 
calculate list = [calculateSingle list i | i <- [0..(length list - 1)]]

calculateSingle :: [(Point, Velocity)] -> Int -> (Point, Velocity)
calculateSingle list idx = (calcP, calcVel)
  where
    calcP = zipWith (+) point calcVel   
    calcVel = zipWith (+) newVel (snd $ list !! idx)
    newVel = [vel' 0, vel' 1, vel' 2]
    vel' x = sum (map (\a -> compare (point !! x) (a !! x)) (map fst list))
    point = fst $ list !! idx
    compare a b
      | a == b = 0
      | a < b = 1
      | otherwise = -1

energy :: [(Point, Velocity)] -> Int
energy [] = 0
energy ((points, velocity):xs) = (sum (map (abs) points)) * (sum (map (abs) velocity)) + (energy xs)

sample :: [(Point, Velocity)]
sample = [([(-1), 0, 2], [0,0,0]), ([2, (-10), (-7)], [0,0,0]), ([4, (-8), 8], [0,0,0]), ([3, 5, (-1)], [0,0,0])]

origin :: [(Point, Velocity)]
origin = [([(-4), 3, 15], [0,0,0]), ([(-11), (-10), 13], [0,0,0]), ([2, 2, 18], [0,0,0]), ([7, (-1), 0], [0,0,0])]

parsePoints :: [String] -> [(Point, Velocity)]
parsePoints [] = []
parsePoints (line:xs) = ([toInt 1, toInt 3, toInt 5], [0,0,0]) : (parsePoints xs)
  where
    toInt idx = read (parseLine !! idx)
    parseLine = concat $ map (\x -> split x '=') $ split (concat $ split line '>') ','
  
{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Lines (Just lines)) = energy ((iterate calculate (parsePoints lines)) !! 1000)

-- Solve second challenge
-- solve2 :: Input -> Point
-- solve2 (Lines (Just lines)) = 

-- Print challenge result
main = do
  solve "Energy after 1000 steps" (Lines Nothing) (solve1)
  -- solve "TODO"              (Lines Nothing) (solve2)