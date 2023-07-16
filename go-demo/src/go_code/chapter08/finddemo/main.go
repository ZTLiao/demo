package main

import "fmt"

func main() {
	names := [4]string{"白眉鹰王", "金毛狮王", "紫衫龙王", "青翼蝠王"}
	var heroName = ""
	fmt.Println("input name:")
	fmt.Scanln(&heroName)
	for i := 0; i < len(names); i++ {
		if heroName == names[i] {
			fmt.Printf("find heroName : %v, i : %v\n", heroName, i)
			break
		} else if i == (len(names) - 1) {
			fmt.Printf("no find %v\n", heroName)
		}
	}

	index := -1
	for i := 0; i < len(names); i++ {
		if heroName == names[i] {
			index = i
			fmt.Printf("find heroName : %v, i : %v\n", heroName, i)
			break
		}
	}
	if index != -1 {
		fmt.Printf("find heroName : %v, i : %v\n", heroName, index)
	}
	fmt.Println("index : ", index)
}
