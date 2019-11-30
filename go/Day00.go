package main

import (
	"fmt"
	"io/ioutil"
	"os"
)

func check(e error) {
	if e != nil {
		panic(e)
	}
}

func main() {
	// use filepath from os args
	filepath := os.Args[1]

	// read file
	file, err := ioutil.ReadFile(filepath)
	check(err)

	// file content to string
	content := string(file)

	fmt.Println(content)
}
