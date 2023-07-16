package main

import "fmt"

func main() {
	var a map[string]string
	a = make(map[string]string, 10)
	a["no1"] = "songjiang"
	fmt.Println(a)

	cities := make(map[string]string)
	cities["no1"] = "beijing"
	cities["no2"] = "beijing"
	cities["no3"] = "beijing"
	fmt.Println(cities)

	heroes := map[string]string{
		"hero1": "songjiang",
		"hero2": "lujunyi",
	}
	fmt.Println(heroes)

	studentMap := make(map[string]map[string]string)
	studentMap["stu01"] = make(map[string]string, 2)
	studentMap["stu01"]["name"] = "tom"
	studentMap["stu01"]["sex"] = "nan"

	fmt.Println(studentMap)
}
