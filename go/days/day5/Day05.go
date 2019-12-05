package day5

import (
	"AdventOfCode-2019/util"
	"fmt"
	"strconv"
	"strings"
)

func Execute(fileContent *string) {
	//fmt.Println(processInstructions(util.StrArrToIntArr(strings.Split("1,9,10,3,2,3,11,0,99,30,40,50", ","))))
	//fmt.Println(processInstructions(util.StrArrToIntArr(strings.Split("1002,4,3,4,33", ","))))
	//return

	if fileContent == nil {
		panic("File not found.")
	}

	// file content to string
	content := strings.Split(*fileContent, ",")

	// use file content
	fmt.Printf("First output: %d\n", processInstructions(util.StrArrToIntArr(content)))
}

func processInstructions(intArray []int) int {
	steps := len(intArray)
	for i := 0; i < steps; i++ {
		operator := intArray[i]
		if ret := processSingle(operator, &i, intArray, "000"); ret != nil {
			return *ret
		}
	}
	return -1
}

func processSingle(operator int, current *int, intArray []int, insertModes string) *int {
	inserter := []rune(insertModes)

	switch operator {
	case 1, 2:
		where := *current + 3
		if string(inserter[0]) == "0" {
			where = intArray[where]
		}

		what1 := *current + 1
		if string(inserter[2]) == "0" {
			what1 = intArray[what1]
		}

		what2 := *current + 2
		if string(inserter[1]) == "0" {
			what2 = intArray[what2]
		}

		if operator == 1 {
			intArray[where] = intArray[what1] + intArray[what2]
		} else {
			intArray[where] = intArray[what1] * intArray[what2]
		}

		*current += 3
	case 3:
		where := *current + 1
		if string(inserter[2]) == "0" {
			where = intArray[where]
		}
		intArray[where] = 1
		*current += 1
	case 4:
		where := *current + 1
		if string(inserter[2]) == "0" {
			where = intArray[where]
		}
		fmt.Print(intArray[where])
		*current += 1
	case 5:
		//Opcode 5 is jump-if-true: if the first parameter is non-zero, it sets the
		// instruction pointer to the value from the second parameter. Otherwise, it does nothing.
	case 6:
		//Opcode 6 is jump-if-false: if the first parameter is zero, it sets the instruction pointer
		// to the value from the second parameter. Otherwise, it does nothing.

	case 7:
		//Opcode 7 is less than: if the first parameter is less than the second parameter,
		// it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
	case 8:
		//Opcode 8 is equals: if the first parameter is equal to the second parameter,
		// it stores 1 in the position given by the third parameter. Otherwise, it stores 0.

	case 99:
		return &intArray[0]
	default:
		nextOperatorString := strconv.Itoa(operator)

		if len(nextOperatorString) == 2 {
			handleUnknownOperator(operator)
		}

		runes := []rune(nextOperatorString)
		safeSubstring := string(runes[len(nextOperatorString)-1 : len(nextOperatorString)])

		if nextOperator, _ := strconv.Atoi(safeSubstring); nextOperator > 0 && nextOperator <= 4 || nextOperator == 99 {
			left := string(runes[:len(nextOperatorString)-2])
			for len(left) < 3 {
				left = "0" + left
			}

			processSingle(nextOperator, current, intArray, left)
		} else {
			handleUnknownOperator(nextOperator)
		}
	}
	return nil
}

func handleUnknownOperator(operator int) {
	panic(fmt.Errorf("Error unknown operator: %d\n", operator))
}
