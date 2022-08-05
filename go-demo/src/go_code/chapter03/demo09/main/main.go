package main

import (
	"fmt"
	"go_code/chapter03/demo09/model"
)

func main() {
	var i int = 10
	fmt.Println("&i = ", &i)

	var ptr *int = &i
	fmt.Println("ptr = ", ptr)
	fmt.Println("&ptr = ", &ptr)

	var num int = 9
	fmt.Printf("num addr = %v", &num)

	ptr = &num
	*ptr = 11
	fmt.Println("num = ", num)

	var a int = 300
	var f float32 = float32(a)
	var ptr1 *float32 = &f
	fmt.Println("ptr1 = ", *ptr1)
	fmt.Println("HeroName = ", model.HeroName)
}
