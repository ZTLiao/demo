package main

import (
	"fmt"
)

func main() {
	fmt.Println("test...")

	var n1 float64 = 1
	var n2 float64 = 2
	var operator byte = '*'
	var res float64

	switch operator {
	case '+':
		res = n1 + n2
	case '-':
		res = n1 - n2
	case '*':
		res = n1 * n2
	case '/':
		res = n1 / n2
	default:
		fmt.Println("operator is error")
	}

	res = calc(n1, n2, operator)

	fmt.Println("res = ", res)
}

func calc(n1 float64, n2 float64, operator byte) float64 {
	var res float64
	switch operator {
	case '+':
		res = n1 + n2
	case '-':
		res = n1 - n2
	case '*':
		res = n1 * n2
	case '/':
		res = n1 / n2
	default:
		fmt.Println("operator is error")
	}
	return res
}
