package main

import (
	"fmt"
)

func test(n int) {
	if n > 2 {
		n--
		test(n)
	}
	fmt.Println("n = ", n)
}

func test2(n int) {
	if n > 2 {
		n--
		test(n)
	} else {
		fmt.Println("n = ", n)
	}
}

func test03(n1 *int) {
	*n1 = *n1 + 10
	fmt.Println("test03 n1 = ", *n1)
}

func getSum(n1 int, n2 int) int {
	return n1 + n2
}

func myFun(funvar func(int, int) int, num1 int, num2 int) int {
	return funvar(num1, num2)
}

type myFunType func(int, int) int

func myFun2(funvar myFunType, num1 int, num2 int) int {
	return funvar(num1, num2)
}

func getSumAndSub(n1 int, n2 int) (sum int, sub int) {
	sum = n1 + n2
	sub = n1 - n2
	return
}

func sum1(n1 int, args ...int) int {
	sum := n1
	for i := 0; i < len(args); i++ {
		sum += args[i]
	}
	return sum
}

func main() {
	test(4)
	test2(4)
	num := 20
	fmt.Printf("num pointer = %v", &num)
	test03(&num)
	fmt.Println("n1 = ", num)
	a := getSum
	fmt.Printf("a = %T, getSum = %T\n", a, getSum)

	res := a(1, 2)
	fmt.Println("res = ", res)

	res2 := myFun(getSum, 50, 60)
	fmt.Println("res2 = ", res2)

	type myInt int
	var num1 myInt
	var num2 int
	num1 = 1
	num2 = int(num1)
	fmt.Println("num1 = ", num1, "num2 = ", num2)

	i, j := getSumAndSub(1, 2)

	fmt.Println("i = ", i, " j = ", j)

	res4 := sum1(1, 2, 3, 4, 5)
	fmt.Println("res4 = ", res4)
}
