package main

import "fmt"

func main() {
	var arr [5]int = [...]int{10, 20, 30, 40, 50}
	//slice := arr[1:4]
	slice := arr[0:len(arr)]
	for i := 0; i < len(slice); i++ {
		fmt.Printf("slice[%v] = %v ", i, slice[i])
	}
	fmt.Println()
	for i, v := range slice {
		fmt.Printf("i = %v, v = %v\n", i, v)
	}
	fmt.Println(slice[1])

	slice2 := slice[1:2]
	slice2[0] = 100
	fmt.Println("slice2 = ", slice2)
	fmt.Println("slice = ", slice)
	fmt.Println("arr = ", arr)

	var slice3 []int = []int{100, 200, 300}
	slice3 = append(slice3, 400, 500, 600)
	slice3 = append(slice3, slice3...)
	fmt.Println("slice3 = ", slice3)
	//fmt.Println("slice4 = ", slice4)
	var slice4 []int = []int{1, 2, 3, 4, 5}
	var slice5 = make([]int, 10)
	copy(slice5, slice4)
	fmt.Println("slice4 = ", slice4)
	fmt.Println("slice5 = ", slice5)
}
