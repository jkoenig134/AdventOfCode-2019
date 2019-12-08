module Main where

import Common

type Layer = [String]

parseWidth :: String -> Int -> Layer
parseWidth [] with = []
parseWidth input width = (take width input) : (parseWidth (drop width input) width)

parseHeight :: [String] -> Int -> [[String]]
parseHeight [] height = []
parseHeight input height = take height input : (parseHeight (drop height input) height)

countInLayer :: Layer -> Char -> Int
countInLayer layer letter = sum (map (\x -> (count x letter)) layer)

find :: [Layer] -> Layer
find layers = snd $ minimum $ [(countInLayer layer '0', layer) | layer <- layers]
{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Line (Just line)) = (countInLayer zeroLayer '1') * (countInLayer zeroLayer '2')
  where
    zeroLayer = find $ parseHeight (parseWidth line 25) 6

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Lines (Just lines)) = 5

-- Print challenge result
main = do
  solve "Transmission verification"        (Line Nothing) (solve1)
  solve "Minimum number of orbital transfers" (Lines Nothing) (solve2)