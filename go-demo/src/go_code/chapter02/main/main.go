package main

import "fmt"

func test(n1 int) {
	n1 = n1 + 1
	fmt.Println("test n1 = ", n1)
}

func getSum(n1 int, n2 int) int {
	sum := n1 + n2
	return sum
}

func getSumAndSub(n1 int, n2 int) (int, int) {
	sum := n1 + n2
	sub := n1 - n2
	return sum, sub
}

func main() {
	n1 := 10
	test(n1)
	fmt.Println("main n1 = ", n1)
	sum := getSum(1, 2)
	fmt.Println("sum = ", sum)
	res1, res2 := getSumAndSub(1, 2)
	fmt.Println("res1 = ", res1, "res2 = ", res2)
}
