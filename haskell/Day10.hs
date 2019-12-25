module Main where

import Common

data Type = Empty | Astereoid deriving (Eq, Show)

type Point  = (Int, Int)
type Map    = [(Point, Type)]

-- Get the type by point
getType :: Map -> Point -> Type
getType []          p = error "Point not on map"
getType ((p',t):xs) p = if (p == p') then t else getType xs p

-- Set the type for a point
setType :: Map -> Point -> Type -> Map
setType list p t = set list (index list 0) (p, t)
  where 
    index []            i = error "point not found"
    index ((p', t'):xs) i = if p' == p then i else index xs (i + 1)    

-- Get the height of the map    
height :: Map -> Int
height []             = error "empty map"
height (((a,b),t):xs) = if (a == 0) then 1 + (height xs) else 0

-- Get the width of the map
width :: Map -> Int
width []             = 0
width (((a,b),t):xs) = if (b == 0) then 1 + (width xs) else 0 + (width xs)

-- Calculate all points between two points by using gradient
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

-- Check if between two points are any asteroids
canSee :: Map -> Point -> Point -> Bool
canSee map' start end = not $ elem Astereoid types
  where
    types = map (getType map') (points start end)

-- Find the perfect station and its observable asteroids
findStation :: Map -> (Int, Point)
findStation map' = maximum [(count p, p) | p <- asteroids]
  where
    count p   = length [p' | p' <- asteroids, canSee map' p p'] - 1
    asteroids = [point | (point,t) <- map', t == Astereoid]

-- Laser all asteroids from a laser location and return its destroyed asteroids
laser :: Map -> Point -> Double -> [Point]
laser map' (x,y) last = eliminated : (laser (setType map' eliminated Empty) (x,y) (calcAngle eliminated))
  where
    eliminated    = (nearest $ fst $ minimum [(calcAngle p, p) | p <- asteroids, (calcAngle p) > last])
    nearest alpha = snd $ minimum [(distance (x,y) p, p) | p <- asteroids, round6dp (alpha) == round6dp (calcAngle p), ((distance (x,y) p) /= 0)]   
    asteroids     = [point | (point,t) <- map', t == Astereoid]
    calcAngle p   = angle (distanceVector (x,y) p)

-- Get the vector between two points
distanceVector :: Point -> Point -> Point
distanceVector (x,y) (x',y') = (x'-x, y-y')

-- Calculate the distance between two points
distance :: Point -> Point -> Double
distance (x,y) (x',y') = distanceH (fromIntegral x) (fromIntegral y) (fromIntegral x') (fromIntegral y')
  where
    distanceH x y x' y' = sqrt ((x-x')^2 + (y-y')^2)

-- Calculate the angle between y-axis and a vector (in degrees)
angle :: Point -> Double
angle (x,y)
  | result < 0 = 360 - (degrees (abs result))
  | otherwise  = degrees result  
  where
    result :: Double
    result = atan2 (fromIntegral x) (fromIntegral y)
    degrees :: Double -> Double
    degrees x = 180 * (x / pi)

-- Parse the map by input string  
parseMap :: [String] -> Map
parseMap lines = [pointAt x y | x <- [0..width], y <- [0..height]]
  where
    width       = length (lines !! 0) - 1
    height      = length lines - 1
    pointAt x y = ((x, y), if ((lines !! y) !! x) == '.' then Empty else Astereoid)
  
{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> (Int, Point)
solve1 (Lines (Just lines)) = findStation map'
  where
    map' = parseMap lines

-- Solve second challenge
solve2 :: Input -> Point
solve2 (Lines (Just lines)) = (laser map' (19,11) (-1)) !! 199
  where
    map' = parseMap lines

-- Print challenge result
main = do
  solve "New station coordinates and its observable asteroids" (Lines Nothing) (solve1)
  solve "Location of the 200th laser'd asteroids"              (Lines Nothing) (solve2)