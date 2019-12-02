package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"strconv"
	"strings"
)

func main() {
    // process(strArrToIntArr(strings.Split("1,9,10,3,2,3,11,0,99,30,40,50", ",")))

	// use filepath from os args
	filepath := os.Args[1]

	// read file
	if file, err := ioutil.ReadFile(filepath); err == nil {
		// file content to string
		content := strings.Split(string(file), ",")

		// use file content
		var intContent = strArrToIntArr(content)

		// first task
		tmp := make([]int, len(intContent))
		copy(tmp, intContent)
		tmp[1] = 12
		tmp[2] = 2
		first := process(tmp)
		fmt.Printf("First output: %d\n", first)

		// second task
		findNumber(intContent)
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

func process(intArray []int) int {
	steps := len(intArray) / 4
	for i := 0;  i<steps; i++ {
		pointer := i*4
		operator := intArray[pointer]
		//fmt.Printf("accessor %d times\n",operator)
		switch operator {
		case 1:
			intArray[intArray[pointer+3]] = intArray[intArray[pointer+1]] + intArray[intArray[pointer+2]]
		case 2:
			intArray[intArray[pointer+3]] = intArray[intArray[pointer+1]] * intArray[intArray[pointer+2]]
		case 99: 
			return intArray[0]
		default:
			fmt.Printf("Error %d\n", operator)
			return -1
		}
	}
	return -1
}

func findNumber(intArray []int) {
	for i := 0;  i<=99; i++ {
		for j := 0;  j<=99; j++ {
			tmp := make([]int, len(intArray))
			copy(tmp, intArray)
			tmp[1] = i
			tmp[2] = j
			if (process(tmp) == 19690720) {
				fmt.Printf("Second output --\nFirst number: %d\nSecond number: %d\nSolution: %d", i, j, 100 * i + j)
			}
		}
	}
}
