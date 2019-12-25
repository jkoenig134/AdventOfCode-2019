module Main where

import Common
import System.Environment

type Layer = [String]

-- Parse a line by splitting after width
parseWidth :: String -> Int -> Layer
parseWidth []    width = []
parseWidth input width = (take width input) : (parseWidth (drop width input) width)

-- Parse a layer by splitting after height 
parseHeight :: Layer -> Int -> [Layer]
parseHeight []    height = []
parseHeight input height = take height input : (parseHeight (drop height input) height)

-- Count a char in a layer
countInLayer :: Layer -> Char -> Int
countInLayer layer letter = sum (map (\x -> (count x letter)) layer)

-- Find the layer with the minimum amount of '0'
find :: [Layer] -> Layer
find layers = snd $ minimum $ [(countInLayer layer '0', layer) | layer <- layers]

-- Cover all layers to get the final layer
cover :: [Layer] -> Layer
cover layers = foldl coverL [] layers
  where
    coverL layerA []     = layerA
    coverL [] layerB     = layerB
    coverL layerA layerB = zipWith coverS layerA layerB
    coverS lineA lineB = zipWith coverC lineA lineB
    coverC a b
      | (a == '2') = b
      | otherwise  = a

-- Print a layer by replacing the codes with actual "pixels"
printLayer :: Layer -> String
printLayer layer = map replace (unlines layer)
  where
    replace a
      | (a == '0') = '█'
      | (a == '1') = ' '
      | otherwise  = a      

{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Line (Just line)) = (countInLayer zeroLayer '1') * (countInLayer zeroLayer '2')
  where
    zeroLayer = find $ parseHeight (parseWidth line 25) 6

-- Solve second challenge
solve2 :: Input -> String
solve2 (Line (Just line)) = printLayer $ cover layers
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