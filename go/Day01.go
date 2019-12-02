package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"strconv"
	"strings"
)

func main() {
	// use filepath from os args
	filepath := os.Args[1]

	// read file
	if file, err := ioutil.ReadFile(filepath); err == nil {
		// file content to string
		content := strings.Split(string(file), "\n")

		// use file content

		var intContent = strArrToIntArr(content)

		// fmt.Println(massToFuel(12))
		// fmt.Println(massToFuel(14))
		// fmt.Println(massToFuel(1969))
		// fmt.Println(massToFuel(100756))

		fmt.Printf("Needed fuel: %v\n", getFuel(intContent))

		// fmt.Println(massToFuelRecursive(14))
		// fmt.Println(massToFuelRecursive(1969))
		// fmt.Println(massToFuelRecursive(100756))

		fmt.Printf("Needed recurstive fuel: %v", getFuelRecursive(intContent))

	} else {
		fmt.Printf("File not found for path \"%s\"\n", filepath)
	}
}

func strArrToIntArr(content []string) []int {
	var intContent = []int{}

	for _, i := range content {
		j, err := strconv.Atoi(i)
		if err != nil {
			panic(err)
		}
		intContent = append(intContent, j)
	}
	return intContent
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
