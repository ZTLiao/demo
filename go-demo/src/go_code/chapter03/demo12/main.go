package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	var i int = 1
	for {
		if i > 10 {
			break
		}
		fmt.Println("hello, world!")
		i++
	}
	fmt.Println("i = ", i)

	i = 1
	for {
		fmt.Println("hello, ok!")
		i++
		if i > 10 {
			break
		}
	}
	fmt.Println("i = ", i)

	rand.Seed(time.Now().Unix())
	n := rand.Intn(100) + 1
	fmt.Println("n = ", n)

	//label1:
	for i := 0; i < 4; i++ {
	label2:
		for j := 0; j < 10; j++ {
			if j == 2 {
				//break
				//break label1
				break label2
			}
			fmt.Println("j = ", j)
		}
	}
}
