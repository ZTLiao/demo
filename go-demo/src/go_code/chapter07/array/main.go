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
}
