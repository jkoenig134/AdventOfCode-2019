package day1

import (
	"AdventOfCode-2019/util"
	"fmt"
	"strings"
)

func Execute(fileContent *string) {
	if fileContent == nil {
		panic("File not found.")
	}

	// file content to string
	content := strings.Split(*fileContent, "\n")

	// use file content

	var intContent = util.StrArrToIntArr(content)

	// fmt.Println(massToFuel(12))
	// fmt.Println(massToFuel(14))
	// fmt.Println(massToFuel(1969))
	// fmt.Println(massToFuel(100756))

	fmt.Printf("Needed fuel: %v\n", getFuel(intContent))

	// fmt.Println(massToFuelRecursive(14))
	// fmt.Println(massToFuelRecursive(1969))
	// fmt.Println(massToFuelRecursive(100756))

	fmt.Printf("Needed recurstive fuel: %v", getFuelRecursive(intContent))
}

func getFuel(values []int) int {
	fuel := 0

	for _, mass := range values {
		fuel += massToFuel(mass)
	}

	return fuel
}

func massToFuel(mass int) int {
	return mass/3.0 - 2
}

func getFuelRecursive(values []int) int {
	fuel := 0

	for _, mass := range values {
		fuel += massToFuelRecursive(mass)
	}

	return fuel
}

func massToFuelRecursive(mass int) int {
	fuel := massToFuel(mass)

	if fuel <= 0 {
		return 0
	}

	return fuel + massToFuelRecursive(fuel)
}
