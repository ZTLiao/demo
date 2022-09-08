package main

import (
	"fmt"
	"strconv"
)

func main() {
	str := "hello"
	fmt.Println("str len = ", len(str))
	str2 := "hello,world"
	r := []rune(str2)
	for i := 0; i < len(str); i++ {
		fmt.Printf("i = %v\n", r[i])
	}

	n, err := strconv.Atoi("123")
	if err != nil {
		fmt.Println("convert error", err)
	} else {
		fmt.Println("n = ", n)
	}

	str3 := strconv.Itoa(12345)
	fmt.Println("str3 = ", str3)
}
