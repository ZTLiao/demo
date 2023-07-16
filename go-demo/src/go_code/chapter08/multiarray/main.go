package main

import "fmt"

func main() {
	var arr [4][6]int
	arr[1][2] = 1
	arr[2][1] = 2
	arr[2][4] = 3
	fmt.Println(arr)
	for i := 0; i < 4; i++ {
		for j := 0; j < 6; j++ {
			fmt.Print(arr[i][j], " ")
		}
		fmt.Println()
	}

	fmt.Println()

	var arr2 [2][3]int
	arr2[1][1] = 10
	fmt.Println(arr2)

	fmt.Printf("arr2[0] : %p", &arr2[0])
	fmt.Printf("arr2[1] : %p", &arr2[1])
	fmt.Printf("arr2[0][0] : %p", &arr2[0][0])
	fmt.Printf("arr2[1][0] : %p", &arr2[1][0])

	var arr3 [2][3]int = [...][3]int{{1, 2, 3}, {4, 5, 6}}
	fmt.Println("arr3 : ", arr3)
}
