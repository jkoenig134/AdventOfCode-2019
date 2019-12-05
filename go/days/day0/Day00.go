package day0

import (
	"fmt"
	"strings"
)

func Execute(fileContent *string) {
	if fileContent == nil {
		panic("File not found.")
	}

	// file content to string
	content := strings.Split(*fileContent, "\n")

	// use file content
	fmt.Println(content)
}
