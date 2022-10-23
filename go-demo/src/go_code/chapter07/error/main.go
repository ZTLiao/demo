package main

import (
	"errors"
	"fmt"
)

func test() {
	defer func() {

		if err := recover(); err != nil {
			fmt.Printf("err: %v\n", err)
		}
	}()
	num2 := 0
	fmt.Printf("res = %v\n", 1/num2)
}

func readConf(name string) (err error) {
	if name == "config.ini" {
		return nil
	} else {
		return errors.New("file error")
	}
}

func main() {
	test()
	err := readConf("config")
	if err != nil {
		panic(err)
	}
	fmt.Println("test...")
}
