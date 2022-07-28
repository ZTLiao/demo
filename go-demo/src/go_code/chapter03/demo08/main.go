package main

import (
	"fmt"
	"strconv"
)

func main() {
	var n1 int32 = 12
	var n2 int64
	var n3 int8

	n2 = int64(n1) + 20
	n3 = int8(n1) + 20
	fmt.Printf("n2 = %d, n3 = %d", n2, n3)

	var num1 int = 99
	var num2 float64 = 23.456
	var b bool = true
	var myChar byte = 'h'
	var str string
	str = fmt.Sprintf("%d", num1)
	fmt.Printf("str type %T, str = %q\n", str, str)

	str = fmt.Sprintf("%d", num2)
	fmt.Printf("str type %T, str = %v\n", str, str)

	str = fmt.Sprintf("%t", b)
	fmt.Printf("str type %T, str = %q\n", str, str)

	str = fmt.Sprintf("%c", myChar)
	fmt.Printf("str type %T, str = %q\n", str, str)

	var num3 int = 99
	var num4 float64 = 23.456
	var b2 bool = true

	str = strconv.FormatInt(int64(num3), 10)
	fmt.Printf("str type is %T, str = %q\n", str, str)

	str = strconv.FormatFloat(num4, 'f', 10, 64)
	fmt.Printf("str type is %T, str = %q\n", str, str)

	str = strconv.FormatBool(b2)
	fmt.Printf("str type is %T, str = %q\n", str, str)

	var num5 int = 4567
	str = strconv.Itoa(num5)
	fmt.Printf("str type is %T, str = %q\n", str, str)

	var str6 string = "true"
	var b6 bool
	b6, _ = strconv.ParseBool(str6)
	fmt.Printf("b6 type %T, b  = %v", b6, b6)

	var str7 string = "12345"
	var n7 int64
	n7, _ = strconv.ParseInt(str7, 0, 64)
	fmt.Printf("n7 type %T, n7 = %v", n7, n7)

	var str8 string = "123.45"
	var n8 float64
	n8, _ = strconv.ParseFloat(str8, 64)
	fmt.Printf("n8 type %T, n8 = %v", n8, n8)

}
