package main

import "fmt"

func main() {
	var a map[string]string
	a = make(map[string]string, 10)
	a["no1"] = "songjiang"
	fmt.Println(a)
}
