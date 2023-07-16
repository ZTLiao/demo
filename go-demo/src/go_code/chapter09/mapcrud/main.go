package main

import "fmt"

func main() {
	cities := make(map[string]string)
	cities["no1"] = "beijing"
	cities["no2"] = "beijing"
	cities["no3"] = "beijing"
	fmt.Println(cities)

	cities["no3"] = "beijing!"
	fmt.Println(cities)

	delete(cities, "no3")
	fmt.Println(cities)

	val, findRes := cities["no1"]
	if findRes {
		fmt.Println(val)
	} else {
		fmt.Println("error")
	}
}
