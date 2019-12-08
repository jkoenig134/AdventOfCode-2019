module Main where

import Common
import System.Environment

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

cover :: Layer -> Layer -> Layer
cover layerA [] = layerA
cover [] layerB = layerB
cover layerA layerB = layerC
  where
    layerC = zipWith sCover layerA layerB

printLayer :: Layer -> String
printLayer layer = map replace (unlines layer)
  where
    replace a
      | (a == '0') = '█'
      | (a == '1') = ' '
      | otherwise  = a      
      
sCover line line2 = zipWith cover2 line line2

cover2 a b
  | (a == '2')  = b
  | otherwise = a

coverAll :: [Layer] -> Layer
coverAll layers = foldl cover [] layers

{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Line (Just line)) = (countInLayer zeroLayer '1') * (countInLayer zeroLayer '2')
  where
    zeroLayer = find $ parseHeight (parseWidth line 25) 6

-- Solve second challenge
solve2 :: Input -> String
solve2 (Line (Just line)) = printLayer $ coverAll layers
  where
    layers = parseHeight (parseWidth line 25) 6

-- Print challenge result
main = do
  solve "Transmission verification" (Line Nothing) (solve1)
  args <- getArgs
  line <- readLine $ head args
  let result = solve2 (Line (Just line))
  putStr "Decoded image:\n"
  putStr result