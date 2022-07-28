package main

import "fmt"

var n1 = 100
var n2 = 200
var name = "jack"

var (
	n3    = 300
	n4    = 900
	name2 = "mary"
)

func main() {
	//	var n1, n2, n3 int
	//	fmt.Println("n1=", n1, "n2=", n2, "n3=", n3)
	//	var n1, name, n3 = 100, "tom", 888
	//	fmt.Println("n1=", n1, "name=", name, "n3=", n3)

	n1, name, n3 := 100, "tom", 000
	fmt.Println("n1=", n1, "name=", name, "n3=", n3)
	fmt.Println("n3=", n3, "name2=", name2, "n4=", n4)

	var a int
	var b float32
	var c float64
	var isMarried bool
	var name1 string
	fmt.Printf("a = %d, b = %f, c = %f, isMarried =%v, name1 = %v", a, b, c, isMarried, name1)
}
