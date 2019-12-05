package main

import (
	"AdventOfCode-2019/days/day0"
	"AdventOfCode-2019/days/day1"
	"AdventOfCode-2019/days/day2"
	"AdventOfCode-2019/days/day3"
	"AdventOfCode-2019/days/day4"
	"AdventOfCode-2019/days/day5"
	"AdventOfCode-2019/util"
	"os"
)

func main() {
	content := util.ReadFile()

	switch os.Args[1] {
	case "Day00":
		day0.Execute(content)
	case "Day01":
		day1.Execute(content)
	case "Day02":
		day2.Execute(content)
	case "Day03":
		day3.Execute(content)
	case "Day04":
		day4.Execute()
	case "Day05":
		day5.Execute(content)
	}
}
