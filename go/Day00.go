package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"strings"
)

func main() {
	// use filepath from os args
	filepath := os.Args[1]

	// read file
	if file, err := ioutil.ReadFile(filepath); err == nil {
		// file content to string
		content := strings.Split(string(file), "\n")

		// use file content
		fmt.Println(content)
	} else {
		fmt.Printf("File not found for path \"%s\"\n", filepath)
	}

}
