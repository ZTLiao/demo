package main

import (
	"fmt"
)

func main() {

	fmt.Println("ok1...")
	goto label
	fmt.Println("ok2...")
	fmt.Println("ok3...")
	fmt.Println("ok4...")
	fmt.Println("ok5...")
	fmt.Println("ok6...")
label:
	fmt.Println("ok7...")
	fmt.Println("ok8...")
	fmt.Println("ok9...")
}
