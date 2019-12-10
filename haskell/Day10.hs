module Main where

import Common

data Type = Empty | Astereoid deriving (Eq, Show)

type Point = (Int, Int)
type Map = [(Point, Type)]

getType :: Map -> Point -> Type
getType [] p = error "Point not on map"
getType ((p',t):xs) p = if (p == p') then t else getType xs p

setType :: Map -> Point -> Type -> Map
setType list p t = set list (index list 0) (p, t)
  where 
    index [] i = error "point not found"
    index ((p', t'):xs) i = if p' == p then i else index xs (i + 1)    
    
height :: Map -> Int
height [] = error "empty map"
height (((a,b),t):xs) = if (a == 0) then 1 + (height xs) else 0

width :: Map -> Int
width [] = 0
width (((a,b),t):xs) = if (b == 0) then 1 + (width xs) else 0 + (width xs)

points :: Point -> Point -> [Point]
points (a,b) (x,y)
  | (a == x) && (b < y) = [(a, b + k) | k <- [1..(y-b-1)]]
  | (a == x) && (b > y) = [(a, y + k) | k <- [1..(b-y-1)]]
  | (b == y) && (a < x) = [(a + k, b) | k <- [1..(x-a-1)]]
  | (b == y) && (a > x) = [(x + k, b) | k <- [1..(a-x-1)]]
  | (x < a && b > y) && (gcd xDiff yDiff) > 1 = [(a - k*reduceX, b - k*reduceY) | k <- [1..((gcd xDiff yDiff) - 1)]]
  | (x < a && b < y) && (gcd xDiff yDiff) > 1 = [(a - k*reduceX, b + k*reduceY) | k <- [1..((gcd xDiff yDiff) - 1)]]
  | (a < x && b < y) && (gcd xDiff yDiff) > 1 = [(a + k*reduceX, b + k*reduceY) | k <- [1..((gcd xDiff yDiff) - 1)]]
  | (a < x && b > y) && (gcd xDiff yDiff) > 1 = [(a + k*reduceX, b - k*reduceY) | k <- [1..((gcd xDiff yDiff) - 1)]]
  | otherwise = []
  where
    reduceX = xDiff `div` (gcd xDiff yDiff)
    reduceY = yDiff `div` (gcd xDiff yDiff)
    xDiff = abs (x-a)
    yDiff = abs (y-b)

canSee :: Map -> Point -> Point -> Bool
canSee map' start end = not $ elem Astereoid types
  where
    types = map (getType map') (points start end)

-- findStation :: Map -> Point
findStation map' = fst $ maximum [(count p, p) | p <- asteroids]
  where
    count p = length [p' | p' <- asteroids, canSee map' p p'] - 1
    asteroids = [point | (point,t) <- map', t == Astereoid]

laser :: Map -> Point -> Int -> [Point]
laser map' (x,y) 5000 = []
laser map' (x,y) i = case getAst (points (x,y) (boarder !! (i `mod` (length boarder)))) of Nothing -> laser map' (x,y) (i + 1)
                                                                                           Just (a,b) -> (a,b) : (laser (setType map' (a,b) Empty) (x,y) (i + 1))
  where
    getAst []   = Nothing
    getAst ((a,b):xs) = if (getType map' (a,b)) == Astereoid then Just (a,b) else getAst xs
    
    boarder = [(x + i, 0) | i <- [0..(w - x)]] ++ [(w, i) | i <- [1..h]] ++ [(w - i, h) | i <- [1..w]] ++ [(0, h-i) | i <- [1..h]] ++ [(i,0) | i <- [1..(x-1)]]
    w = (width map') - 1
    h = (height map') - 1
  
parseMap :: [String] -> Map
parseMap lines = [pointAt x y | x <- [0..width], y <- [0..height]]
  where
    width = length (lines !! 0) - 1
    height = length lines - 1
    pointAt x y = ((x, y), if ((lines !! y) !! x) == '.' then Empty else Astereoid)
  
{- Handle input and solve executions -}

-- Solve first challenge
-- solve1 :: Input -> Point
solve1 (Lines (Just lines)) = findStation map'
  where
    map' = parseMap lines

mapIt :: Map
mapIt = [((0,0),Empty),((0,1),Empty),((0,2),Astereoid),((0,3),Empty),((0,4),Empty),((1,0),Astereoid),((1,1),Empty),((1,2),Astereoid),((1,3),Empty),((1,4),Empty),((2,0),Empty),((2,1),Empty),((2,2),Astereoid),((2,3),Empty),((2,4),Empty),((3,0),Empty),((3,1),Empty),((3,2),Astereoid),((3,3),Empty),((3,4),Astereoid),((4,0),Astereoid),((4,1),Empty),((4,2),Astereoid),((4,3),Astereoid),((4,4),Astereoid)]

-- Solve second challenge
-- solve2 :: Input -> Int
solve2 (Lines (Just lines)) = (laser map' (8,3) 0)
  where
    map' = parseMap lines

-- Print challenge result
main = do
  solve "New station coordinates"           (Lines Nothing) (solve1)
  solve "TODO" (Lines Nothing) (solve2)