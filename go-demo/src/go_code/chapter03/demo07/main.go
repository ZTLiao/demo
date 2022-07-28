package main

import "fmt"

func main() {
	var i int = 100
	var n1 float32 = float32(i)
	var n2 int8 = int8(i)
	var n3 int64 = int64(i)
	fmt.Printf("i = %v, n1 = %v, n2 = %v, n3 = %v\n", i, n1, n2, n3)

	fmt.Printf("i type is %T", i)

	var num1 int64 = 99999
	var num2 int8 = int8(num1)
	fmt.Println("num2 = ", num2)
}
