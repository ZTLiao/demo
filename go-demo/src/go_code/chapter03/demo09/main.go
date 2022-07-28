package main

import (
	"fmt"
)

func main() {
	var i int = 10
	fmt.Println("&i = ", &i)

	var ptr *int = &i
	fmt.Println("ptr = ", ptr)
	fmt.Println("&ptr = ", &ptr)
}
