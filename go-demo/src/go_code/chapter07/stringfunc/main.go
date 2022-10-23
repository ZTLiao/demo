package main

import (
	"fmt"
	"strconv"
	"strings"
)

func main() {
	str := "hello"
	fmt.Println("str len = ", len(str))
	str2 := "hello,world"
	r := []rune(str2)
	for i := 0; i < len(str); i++ {
		fmt.Printf("i = %v\n", r[i])
	}

	n, err := strconv.Atoi("123")
	if err != nil {
		fmt.Println("convert error", err)
	} else {
		fmt.Println("n = ", n)
	}

	str3 := strconv.Itoa(12345)
	fmt.Println("str3 = ", str3)

	var bytes = []byte("hello go")
	fmt.Printf("bytes = %v\n", bytes)

	str3 = string([]byte{97, 98, 99})
	fmt.Printf("str3 = %v\n", str3)

	str = strconv.FormatInt(123, 16)
	fmt.Printf("123 对应 16进制 = %v\n", str)

	str = strconv.FormatInt(123, 2)
	fmt.Printf("123 对应 2 = %v\n", str)

	b := strings.Contains("seafood", "foo")
	fmt.Printf("b = %v\n", b)

	num := strings.Count("ceheese", "e")
	fmt.Printf("num = %v\n", num)

	b = strings.EqualFold("abc", "Abc")
	fmt.Printf("b = %v\n", b)

	b = "abc" == "Abc"

	fmt.Println("result = ", ("abc" == "Abc"))

	index := strings.Index("NLT_abc", "abc")
	fmt.Printf("index = %v\n", index)

	index = strings.LastIndex("go golang", "go")
	fmt.Printf("index = %v\n", index)

	str = strings.Replace("go go hello", "go", "go语言", -1)
	fmt.Printf("str = %v\n", str)

	strArr := strings.Split("hello,world,ok", ",")
	fmt.Printf("strArr = %v\n", strArr)

	for i := 0; i < len(strArr); i++ {
		fmt.Printf("i = %v\n", strArr[i])
	}

	str = "goLang hello"
	str = strings.ToLower(str)
	str = strings.ToUpper(str)
	fmt.Println(str)

	str = strings.TrimSpace(" tn alone gopher ntrn ")
	fmt.Printf("str = %q\n", str)

	str = strings.Trim("!hello! ", " !")
	fmt.Printf("str = %q\n", str)

	b = strings.HasPrefix("ftp://192.168.0.1", "ftp")
	fmt.Printf("b = %v\n", b)

}
