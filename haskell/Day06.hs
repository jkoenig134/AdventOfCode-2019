module Main where

import Common

-- Edge with start and end vertex
data Edge t = Edge t t deriving (Show, Eq)

-- Count all (transitive) edges that ends in the end vertex
count :: (Eq t) => [Edge t] -> t -> Int
count edges end = helper edges edges end
  where
    helper total [] end = 0
    helper total ((Edge a b):xs) end
      | (b == end) =  1 + (helper total total a) + (helper total xs end)
      | otherwise = helper total xs end

points :: (Eq t) => [Edge t] -> t -> t -> [t]
points edges start end = helper edges edges start end
  where
    helper total [] start end = []
    helper total ((Edge a b):xs) start end
      | (a == start && b == end) = [a, b]
      | (b == end) =  [b] ++ (helper total total start a) ++ (helper total xs start end)
      | otherwise = helper total xs start end

intersect :: [Edge String] -> [String]
intersect edges = [a | a <- points edges "COM" "YOU", elem a (points edges "COM" "SAN")]

distance :: (Eq t) => [Edge t] -> t -> t -> Int
distance edges start end = length (points edges start end) - 1


minPoint :: (Ord t) => [Edge t] -> [t] -> t -> t
minPoint edges vertexes center = snd $ minimum $ map (\x ->  (distance edges x center, x)) vertexes


{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Lines (Just lines)) = sum (map (count edges) targets)
  where
    targets = map (\(Edge a b) -> b) edges
    edges = map parseEdge lines
    parseEdge line = Edge ((split line ')') !! 0) ((split line ')') !! 1)

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Lines (Just lines)) = (distance edges (minP "YOU") "YOU") + (distance edges (minP "SAN") "SAN") - 2
  where
    minP = minPoint edges (intersect edges)
    targets = map (\(Edge a b) -> b) edges
    edges = map parseEdge lines
    parseEdge line = Edge ((split line ')') !! 0) ((split line ')') !! 1)

-- Print challenge result
main = do
  solve "Number of (in-)direct orbits" (Lines Nothing) (solve1)
  solve "Minimum number of orbital transfers" (Lines Nothing) (solve2)