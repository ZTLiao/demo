package main

import "fmt"

func main() {
	var hens [6]float64
	hens[0] = 1.0
	hens[1] = 1.0
	hens[2] = 1.0
	hens[3] = 1.0
	hens[4] = 1.0
	hens[5] = 1.0
	totalWeight := 0.0
	for i := 0; i < len(hens); i++ {
		totalWeight += hens[i]
	}
	fmt.Printf("totalWeight = %v\n", totalWeight)

	var score [5]float64
	for i := 0; i < len(score); i++ {
		fmt.Printf("%d = \n", i+1)
		fmt.Scanln(&score[i])
	}
	for i := 0; i < len(score); i++ {
		fmt.Printf("score[%d] = %v\n", i, score[i])
	}

	var numArr01 [3]int = [3]int{1, 2, 3}
	fmt.Println("numArr01 = ", numArr01)

	var numArr02 = [3]int{1, 2, 3}
	fmt.Println("numArr02 = ", numArr02)

	var numArr03 = [...]int{1, 2, 3}
	fmt.Println("numArr03 = ", numArr03)

	var numArr04 = [...]int{1: 1, 0: 0, 2: 2}
	fmt.Println("numArr04 = ", numArr04)

	for i, v := range numArr01 {
		fmt.Println("i = ", i, ", v = ", v)
	}

	test01(numArr01)
	test02(&numArr01)
}

func test01(arr [3]int) {
	arr[0] = 100
}

func test02(arr *[3]int) {
	(*arr)[0] = 200
}
