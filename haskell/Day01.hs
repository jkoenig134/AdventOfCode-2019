module Main where

import Common

-- Calculate the fuel for a list of masses
sumFuel :: [Int] -> Int
sumFuel list = sum (map calculateFuel list)

-- Calculate the extended fuel for a list of masses
sumExtendedFuel :: [Int] -> Int
sumExtendedFuel list = sum (map calculateExtendendFuel list)

-- Calculate the fuel by mass (and fuel mass,...)
calculateExtendendFuel :: Int -> Int
calculateExtendendFuel mass = if fuel <= 0 then 0 else fuel + (calculateExtendendFuel fuel)
  where fuel = calculateFuel mass

-- Calculate the fuel by mass
calculateFuel :: Int -> Int
calculateFuel mass = mass `div` 3 - 2

{- Handle input and solve executions -}

-- Solve first challenge
solve1 :: Input -> Int
solve1 (Lines (Just list)) = sumFuel $ map read list

-- Solve second challenge
solve2 :: Input -> Int
solve2 (Lines (Just list)) = sumExtendedFuel $ map read list

-- Print challenge result
main = do
  solve "Sum of fuel requirements" (Lines Nothing) (solve1)
  solve "Sum of extended fuel requirements" (Lines Nothing) (solve2)