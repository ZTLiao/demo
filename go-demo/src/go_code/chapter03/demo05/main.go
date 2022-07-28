package main

import (
	"fmt"
	"unsafe"
)

func main() {
	var i = 1
	var j = 2
	var r = i + j
	fmt.Println("r = ", r)

	var str1 = "hello "
	var str2 = "world"
	var res = str1 + str2
	fmt.Println("res = ", res)

	var i1 int = 1
	fmt.Println("i1 = ", i1)

	var j1 int8 = -128
	fmt.Println("j1 = ", j1)

	var k uint8 = 255
	fmt.Println("k = ", k)

	var a int = 8900
	fmt.Println("a = ", a)

	var b uint = 1
	var c byte = 255
	fmt.Println("b=", b, "c=", c)

	var n1 = 100
	fmt.Println("n1=", n1)

	fmt.Printf("%T\n", n1)

	var n2 int64 = 10
	fmt.Printf("n2 = %T, %d\n", n2, unsafe.Sizeof(n2))

	var age uint8 = 100
	fmt.Printf("age = %d\n", age)

	var price float32 = 89.12
	fmt.Println("price = ", price)

	var num1 float32 = -0.00089
	var num2 float64 = -0.00089

	fmt.Println("num1 = ", num1, "num2 = ", num2)

	var num3 float32 = -123.0000901
	var num4 float32 = -123.0000901
	fmt.Println("num3 =", num3, "num4 =", num4)

	var num5 = 1.1
	fmt.Printf("%T\n", num5)

	num6 := 5.12
	num7 := .123
	fmt.Println("num6 =", num6, "num7 =", num7)

	num8 := 5.1234e2
	num9 := 5.1234e2
	num10 := 5.1234e-2
	fmt.Println("num8 = ", num8, "num9 =", num9, "num10 = ", num10)

	var c1 byte = 'a'
	var c2 byte = '0'

	fmt.Println("c1 = ", c1, "c2 = ", c2)
	fmt.Printf("c1 = %c, c2 = %c\n", c1, c2)

	var c3 int = '北'
	fmt.Printf("c3 = %c, c3 = %d\n", c3, c3)

	var c4 int = 22269
	fmt.Printf("c4 = %c\n", c4)

	var n10 = 10 + 'a'
	fmt.Println("n10 = ", n10)

	var bl = false
	fmt.Println("bl = ", bl, "unsafe.Sizeof(bl) = ", unsafe.Sizeof(bl))

	var addr string = "广州"

	fmt.Println("addr = ", addr)

	var str = "hello"

	//str[0] = 'a'
	fmt.Println("str = ", str)

	str3 := "abc\nabc"
	fmt.Print("str3 = ", str3)

	str4 := `test hello world`
	fmt.Println("str4 = ", str4)
}
