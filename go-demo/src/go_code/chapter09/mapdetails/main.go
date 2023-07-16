package main

import (
	"fmt"
)

func modify(map1 map[int]int) {
	map1[10] = 9000
}

type Stu struct {
	Name    string
	Age     int
	Address string
}

func main() {
	map1 := make(map[int]int)
	map1[1] = 90
	map1[2] = 88
	map1[10] = 1
	map1[20] = 2
	modify(map1)
	fmt.Println(map1)

	students := make(map[string]Stu, 10)
	stu1 := Stu{
		"xiaobei",
		10,
		"beijing",
	}
	students["no1"] = stu1
	fmt.Println(students)

	for k, v := range students {
		fmt.Printf("k = %v, v = %v, name = %v, age = %v, address = %v\n", k, v, v.Name, v.Age, v.Address)
	}
}
