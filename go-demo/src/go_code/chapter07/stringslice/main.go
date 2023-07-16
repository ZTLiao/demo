package main

import "fmt"

func main() {
	str := "hello@atguigu.com"
	slice := str[6:]
	fmt.Println("slice = ", slice)
	//str[0] = 'z'
	//slice2 := str[:]
	arr1 := []byte(str)
	arr1[0] = 'z'
	str = string(arr1)
	fmt.Println("str = ", str)
}
