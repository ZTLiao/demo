package main

import (
	"fmt"
	util "go_code/chapter04/utils"
)

func main() {
	var n1 float64 = 1.2
	var n2 float64 = 2.3
	var operator byte = '+'
	var res = util.Calc(n1, n2, operator)
	fmt.Println("res = ", res)
	fmt.Println("Num = ", util.Num)
}
