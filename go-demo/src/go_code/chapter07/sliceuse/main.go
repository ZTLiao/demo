package main

import "fmt"

func main() {
	var slice []float64 = make([]float64, 5, 10)
	slice[1] = 10
	slice[3] = 4
	fmt.Println("slice = ", slice)
	fmt.Printf("slice len = %v, cap = %v\n", len(slice), cap(slice))
	var strslice []string = []string{"tom", "jack", "mary"}
	fmt.Printf("strslice = %v, strslice len = %v, strslice cap = %v\n", strslice, len(strslice), cap(strslice))
}
