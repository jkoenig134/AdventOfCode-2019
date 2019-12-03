module Main where

import Common

data Wire = L Int | R Int | U Int | D Int deriving Show
type Point = (Int,Int)

parseWire :: String -> Wire
parseWire ('L':dist) = L (read dist)
parseWire ('R':dist) = R (read dist)
parseWire ('D':dist) = D (read dist)
parseWire ('U':dist) = U (read dist)

split :: String -> Char -> [String]
split [] c = []
split list c = first : split (drop ((length first) + 1) list) c
  where first = takeWhile (/= c) list
  
idxByVal :: (Eq t) => [t] -> t -> Int
idxByVal [] val = error "value not found"
idxByVal (x:xs) val = search (x:xs) 0
  where search (x:xs) idx = if x == val then idx else search xs (idx + 1)

apply :: Point -> Wire -> [Point]
apply (a,b) (L dist) = [(a - i, b) | i <- [1..dist]]
apply (a,b) (R dist) = [(a + i, b) | i <- [1..dist]]
apply (a,b) (U dist) = [(a, b + i) | i <- [1..dist]]
apply (a,b) (D dist) = [(a, b - i) | i <- [1..dist]]

dist :: Point -> Point -> Int
dist (a,b) (x,y) = (abs (a - x)) + (abs (b - y))

add :: Point -> Point -> Point
add (a,b) (x,y) = (a+x, b+y) 

points :: [Wire] -> Point -> [Point]
points [] prev = []
points [wire] prev = apply prev wire
points (wire:xs) prev = calculated ++ (points xs (last calculated))
  where calculated = apply prev wire

intersect :: [Wire] -> [Wire] -> [Point]
intersect a b = [(x,y) | (x,y) <- pointsA, contains pointsB (x,y)]
  where
    pointsA = points a (0,0)
    pointsB = points b (0,0)
    

    
    contains :: (Eq t) => [t] -> t -> Bool
    contains [] val = False
    contains (x:xs) val = if (x == val) then True else (contains xs val)

    last :: [t] -> t
    last [] = error "empty list"
    last [a] = a
    last (x:xs) = last xs

calcManhattenDist :: [Wire] -> [Wire] -> Int
calcManhattenDist wireA wireB = minimum $ map (dist (0,0)) (intersect wireA wireB)

calcSteps :: [Wire] -> [Wire] -> Int
calcSteps wireA wireB = minimum (map calc (intersect wireA wireB))
  where
    calc point = (idxByVal (points wireA (0,0)) point) + (idxByVal (points wireB (0,0)) point) + 2

{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Lines (Just lines)) = calcManhattenDist wireA wireB
  where
    wireA :: [Wire]
    wireA = map parseWire (split (lines !! 0) ',')
    
    wireB :: [Wire]
    wireB = map parseWire (split (lines !! 1) ',')

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Lines (Just lines)) = calcSteps wireA wireB
  where
    wireA :: [Wire]
    wireA = map parseWire (split (lines !! 0) ',')
    
    wireB :: [Wire]
    wireB = map parseWire (split (lines !! 1) ',')

-- Print challenge result
main = do
  solve "Manhatten distance" (Lines Nothing) (solve1)
  solve "Steps to intersection" (Lines Nothing) (solve2)