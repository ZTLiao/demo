package main

import "fmt"

func main() {
	cities := make(map[string]string)
	cities["no1"] = "beijing"
	cities["no2"] = "beijing"
	cities["no3"] = "beijing"
	fmt.Println(cities)

	for k, v := range cities {
		fmt.Printf("k=%v, v=%v\n", k, v)
	}

	fmt.Println("len = ", len(cities))

	studentMap := make(map[string]map[string]string)
	studentMap["stu01"] = make(map[string]string, 2)
	studentMap["stu01"]["name"] = "tom"
	studentMap["stu01"]["sex"] = "nan"

	for k1, v1 := range studentMap {
		fmt.Println("k1 =", k1)
		for k2, v2 := range v1 {
			fmt.Printf("\tk2 = %v, v2 = %v\n", k2, v2)
		}
		fmt.Println()
	}
}
