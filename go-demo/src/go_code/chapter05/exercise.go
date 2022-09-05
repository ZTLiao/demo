package exercise

import (
	"fmt"
)

func fbn(n int) int {
	if n == 1 || n == 2 {
		return 1
	} else {
		return fbn(n-1) + fbn(n-2)
	}
}

func peach(n int) int {
	if n > 10 || n < 1 {
		fmt.Println(" n is error number")
		return 0
	}
	if n == 10 {
		return 1
	} else {
		return (peach(n+1) + 1) * 2
	}
}
