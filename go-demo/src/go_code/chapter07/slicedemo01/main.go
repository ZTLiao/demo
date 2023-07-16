package main

import "fmt"

func main() {
	var intArr [5]int = [...]int{1, 2, 3, 4, 5}
	slice := intArr[1:3]
	fmt.Println("intArr = ", intArr)
	fmt.Println("slice = ", slice)
	fmt.Println("intArr len = ", len(slice))
	fmt.Println("slice len = ", cap(slice))
}
