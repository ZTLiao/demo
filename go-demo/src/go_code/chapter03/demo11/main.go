package main

import "fmt"

func main() {
	var i int = 5
	fmt.Printf("%b", i)

	var j int = 011
	fmt.Println("j = ", j)

	var k int = 0x11
	fmt.Println("k=", k)

	var str string = "hello,world!"

	for index, val := range str {
		fmt.Printf("index = %d, val = %c\n", index, val)
	}

	str1 := []rune(str)
	for i := 0; i < len(str1); i++ {
		fmt.Printf("val = %c\n", str1[i])
	}
}
