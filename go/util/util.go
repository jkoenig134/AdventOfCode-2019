package util

import (
	"io/ioutil"
	"os"
	"strconv"
)

func StrArrToIntArr(content []string) []int {
	var intContent []int

	for _, i := range content {
		j, err := strconv.Atoi(i)
		if err != nil {
			panic(err)
		}
		intContent = append(intContent, j)
	}
	return intContent
}

func ReadFile() *string {
	// use filepath from os args
	filepath := os.Args[2]
	// read file
	if file, err := ioutil.ReadFile(filepath); err == nil {
		str := string(file)
		return &str
	} else {
		return nil
	}
}
