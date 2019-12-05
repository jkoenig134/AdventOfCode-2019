package day2

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
	content := strings.Split(*fileContent, ",")

	// use file content
	var intContent = util.StrArrToIntArr(content)

	// first task
	tmp := make([]int, len(intContent))
	copy(tmp, intContent)
	tmp[1] = 12
	tmp[2] = 2
	first := process(tmp)
	fmt.Printf("First output: %d\n", first)

	// second task
	findNumber(intContent)
}

func process(intArray []int) int {
	steps := len(intArray) / 4
	for i := 0; i < steps; i++ {
		pointer := i * 4
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
	for i := 0; i <= 99; i++ {
		for j := 0; j <= 99; j++ {
			tmp := make([]int, len(intArray))
			copy(tmp, intArray)
			tmp[1] = i
			tmp[2] = j
			if process(tmp) == 19690720 {
				fmt.Printf("Second output --\nFirst number: %d\nSecond number: %d\nSolution: %d", i, j, 100*i+j)
			}
		}
	}
}
