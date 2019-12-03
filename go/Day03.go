package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"strconv"
	"strings"
)

type Point struct {
	X, Y int
}

type Distance struct {
	wayFromFirst, wayFromSecond int
}

func main() {
	// draw(strings.Split("R8,U5,L5,D3\nU7,R6,D4,L4", "\n")) //6 | 30
	// draw(strings.Split("R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83", "\n")) //159 | 610 -> Fails but is correct!?
	// draw(strings.Split("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\nU98,R91,D20,R16,D67,R40,U7,R15,U6,R7", "\n")) //135 | 410

	// use filepath from os args
	filepath := os.Args[1]

	// read file
	if file, err := ioutil.ReadFile(filepath); err == nil {
		// file content to string
		content := strings.Split(string(file), "\n")

		// use file content
		draw(content)
	} else {
		fmt.Printf("File not found for path \"%s\"\n", filepath)
	}
}

func draw(lines []string) {
	m := make(map[Point]string)
	m[Point{1, 1}] = "o"
	d := make(map[Point]Distance)

	curChar := "."
	lastChar := ""

	for _, str := range lines {
		stepsA := 0
		stepsB := 0
		x := 1
		y := 1
		for _, instruction := range strings.Split(str, ",") {
			runes := []rune(instruction)
			number, err := strconv.Atoi(string(runes[1:]))
			if err != nil {
				panic(err)
			}

			for i := 0; i < number; i++ {
				switch runes[0] {
				case 'R':
					x++
				case 'L':
					x--
				case 'U':
					y++
				case 'D':
					y--
				default:
					fmt.Printf("Unknown Instruction '%s'\n", string(runes[0]))
					os.Exit(0)
				}

				if curChar == "." {
					stepsA++
				} else {
					stepsB++
				}

				curPoint := m[Point{x, y}]

				if curPoint == "" {
					m[Point{x, y}] = curChar
				} else if curPoint == lastChar {
					m[Point{x, y}] = "x"
				}

				curDist := d[Point{x, y}]
				insFirst := 0
				if curDist.wayFromFirst == 0 {
					insFirst = stepsA
				} else {
					insFirst = curDist.wayFromFirst
				}

				insSecond := 0
				if curDist.wayFromSecond == 0 {
					insSecond = stepsB
				} else {
					insSecond = curDist.wayFromSecond
				}

				d[Point{x, y}] = Distance{insFirst, insSecond}
			}
		}

		lastChar = curChar
		curChar = ","
	}

	/* Print output

	for i := 11; i>0; i-- {
		for j := 0; j<12; j++ {
			val := m[Point{i, j}]
			if val == "" {
				fmt.Print("-")
			} else {
				fmt.Print(val)
			}
		}
		fmt.Print("\n")
	}
	*/

	// get nearest point
	currentNearest := -1
	for point, char := range m {
		if (char) == "x" {
			distance := (point.X - 1) + (point.Y - 1)
			if distance < currentNearest || currentNearest < 0 {
				currentNearest = distance
			}
		}
	}

	fmt.Printf("Nearest from start point: %d\n", currentNearest)

	shortestWay := -1
	for point, dist := range d {
		if (m[point]) == "x" {
			distance := dist.wayFromSecond + dist.wayFromFirst
			if distance < shortestWay || shortestWay < 0 {
				shortestWay = distance
			}
		}
	}

	fmt.Printf("Shortest way from start point: %d\n", shortestWay)
}
