package day4

import (
	"fmt"
	"strconv"
)

func Execute() {
	fmt.Printf("111111 should be true: %v\n", checkCode(111111, true))
	fmt.Printf("223450 should be false: %v\n", checkCode(223450, true))
	fmt.Printf("123789 should be false: %v\n", checkCode(123789, true))

	fmt.Printf("112233 should be true: %v\n", checkCode(112233, false))
	fmt.Printf("123444 should be false: %v\n", checkCode(123444, false))
	fmt.Printf("111122 should be true: %v\n", checkCode(111122, false))

	runBetween(357253, 892942)
}

func runBetween(start int, end int) {
	cnt := 0
	for i := start; i <= end; i++ {
		if checkCode(i, true) {
			cnt++
		}
	}
	fmt.Printf("\nNumber of passwords 1: %d\n", cnt)

	cnt = 0
	for i := start; i <= end; i++ {
		if checkCode(i, false) {
			cnt++
		}
	}
	fmt.Printf("\nNumber of passwords 2: %d\n", cnt)
}

func checkCode(code int, ignoreSeries bool) bool {
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
		if ignoreSeries {
			if digit == lastVal {
				same = true
			}
		} else {
			if digit == lastVal {
				seriesLength += 1
			} else {
				same = same || seriesLength == 2
				seriesLength = 1
			}
		}

		lastVal = digit
	}

	return same || seriesLength == 2
}
