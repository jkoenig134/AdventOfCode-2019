package main

import (
	"fmt"
	"strconv"
)

func main() {
	fmt.Printf("111111 should be true: %v\n", checkCode(111111))
	fmt.Printf("223450 should be false: %v\n", checkCode(223450))
	fmt.Printf("123789 should be false: %v\n", checkCode(123789))

	fmt.Printf("112233 should be true: %v\n", checkCode(112233))
	fmt.Printf("123444 should be false: %v\n", checkCode(123444))
	fmt.Printf("111122 should be true: %v\n", checkCode(111122))

	runBetween(357253, 892942)
}

func runBetween(start int, end int) {
	cnt := 0

	//The value is within the range given in your puzzle input.
	for i := start; i <= end; i++ {
		if checkCode(i) {
			cnt++
		}
	}
	fmt.Printf("\nNumber of passwords: %d\n", cnt)
}

func checkCode(code int) bool {
	//It is a six-digit number.
	strVal := strconv.Itoa(code)
	if len(strVal) != 6 {
		return false
	}

	same := false
	lastVal, _ := strconv.Atoi(string(strVal[0]))
	seriesLength := 1

	//Two adjacent digits are the same (like 22 in 122345).
	//Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
	for i := 1; i < len(strVal); i++ {
		digit, _ := strconv.Atoi(string(strVal[i]))
		if digit < lastVal {
			return false
		}

		if digit == lastVal {
			seriesLength += 1
		} else {
			same = same || seriesLength == 2
			seriesLength = 1
		}
		lastVal = digit
	}

	return same || seriesLength == 2
}
