package main

import "fmt"

func main() {
	var scores [3][5]float64
	for i := 0; i < len(scores); i++ {
		for j := 0; j < len(scores[i]); j++ {
			fmt.Printf("input No.%d student of score is %d\n", i+1, j+1)
			fmt.Scanln(&scores[i][j])
		}
	}
	fmt.Println(scores)

	totalSum := 0.0
	for i := 0; i < len(scores); i++ {
		sum := 0.0
		for j := 0; j < len(scores[i]); j++ {
			sum += scores[i][j]
		}
		totalSum += sum
		fmt.Printf("%d.scores sum : %v, avg : %v\n", i+1, sum, sum/float64(len(scores[i])))
	}
	fmt.Printf("sum : %v, avg : %v\n", totalSum, totalSum/15)
}
