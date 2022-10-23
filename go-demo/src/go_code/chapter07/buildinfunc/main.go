package main

import (
	"fmt"
)

func main() {
	num1 := 100
	fmt.Printf("num1 : %T, num1 : %v num1 : %v\n", num1, num1, &num1)

	num2 := new(int)
	*num2 = 100
	fmt.Printf("num2 : %T, num2 : %v num2 : %v, num2 = %v\n", num2, num2, &num2, *num2)
}
