package main

import (
	"fmt"
	"go_code/chapter06/exercise1"
	"strings"
)

var age int = test()

func test() int {
	fmt.Println("test...")
	return 0
}

func init() {
	age = 100
	fmt.Println("init...")
}

var (
	Fun1 = func(n1 int, n2 int) int {
		return n1 * n2
	}
)

func AddUpper() func(int) int {
	var n int = 10
	var str = "hello"
	return func(x int) int {
		n = n + x
		str += string(x)
		fmt.Println("str = ", str)
		return n
	}
}

func makeSuffix(suffix string) func(string) string {

	return func(name string) string {
		if !strings.HasSuffix(name, suffix) {
			return name + suffix
		}
		return name
	}
}

func main() {
	a := 1
	b := 2
	exercise1.Swap(&a, &b)
	fmt.Println("a = ", a, " b = ", b)
	fmt.Println("main ...")
	fmt.Println(" age = ", utils.Age, " name = ", utils.Name)

	res1 := func(n1 int, n2 int) int {
		return n1 + n2
	}(a, b)

	fmt.Println("res1 = ", res1)

	c := func(n1 int, n2 int) int {
		return n1 - n2
	}
	fmt.Println("c = ", c(a, b))

	f := AddUpper()
	fmt.Println(f(1))

	f1 := makeSuffix(".jpg")

	fmt.Println("file name = ", f1("winter"))

}
