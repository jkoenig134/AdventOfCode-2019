package day5

import (
	"AdventOfCode-2019/util"
	"fmt"
	"strings"
)

func Execute(fileContent *string) {
	//fmt.Println(processInstructions(util.StrArrToIntArr(strings.Split("1,9,10,3,2,3,11,0,99,30,40,50", ","))))
	//fmt.Println(processInstructions(util.StrArrToIntArr(strings.Split("1002,4,3,4,33", ","))))
	//fmt.Println(processInstructions(util.StrArrToIntArr(strings.Split("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99", ","))))

	if fileContent == nil {
		panic("File not found.")
	}

	// file content to string
	content := strings.Split(*fileContent, ",")

	// use file content
	processInstructions(util.StrArrToIntArr(content))
	fmt.Println()
}

func processInstructions(intArray []int) int {
	i := 0
	for intArray[i] != 99 {
		if ret := processSingle(&i, intArray); ret != nil {
			return *ret
		}
	}
	return -1
}

func processSingle(current *int, intArray []int) *int {
	operator := intArray[*current] % 100
	mod1 := (intArray[*current] / 100) % 10
	mod2 := (intArray[*current] / 1000) % 10
	mod3 := (intArray[*current] / 10000) % 10

	pointer1 := *current + 1
	if mod1 == 0 {
		pointer1 = intArray[*current+1]
	}

	pointer2 := *current + 2
	if mod2 == 0 {
		pointer2 = intArray[*current+2]
	}

	pointer3 := *current + 3
	if mod3 == 0 {
		pointer3 = intArray[*current+3]
	}

	switch operator {
	case 1:
		intArray[pointer3] = intArray[pointer1] + intArray[pointer2]
		*current += 4
	case 2:
		intArray[pointer3] = intArray[pointer1] * intArray[pointer2]
		*current += 4
	case 3:
		var i int
		fmt.Print("Input: ")
		if _, err := fmt.Scanf("%d", &i); err != nil {
			panic(err)
		}

		intArray[pointer1] = i
		*current += 2
	case 4:
		fmt.Print(intArray[pointer1])
		*current += 2
	case 5:
		if intArray[pointer1] != 0 {
			*current = intArray[pointer2]
		} else {
			*current += 3
		}
	case 6:
		if intArray[pointer1] == 0 {
			*current = intArray[pointer2]
		} else {
			*current += 3
		}
	case 7:
		if intArray[pointer1] < intArray[pointer2] {
			intArray[pointer3] = 1
		} else {
			intArray[pointer3] = 0
		}
		*current += 4
	case 8:
		if intArray[pointer1] == intArray[pointer2] {
			intArray[pointer3] = 1
		} else {
			intArray[pointer3] = 0
		}
		*current += 4
	case 99:
		return &intArray[0]
	default:
		panic(fmt.Errorf("Error unknown operator: %d\n", operator))
	}
	return nil
}
