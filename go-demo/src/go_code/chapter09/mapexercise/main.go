package main

import "fmt"

func modifyUser(users map[string]map[string]string, name string) {
	//v, ok := users[name]
	if users[name] != nil {
		users[name]["password"] = "888888"
	} else {
		users[name] = make(map[string]string)
		users[name]["password"] = "888888"
	}
}

func main() {
	var users map[string]map[string]string
	users = make(map[string]map[string]string, 10)
	modifyUser(users, "tom")
	fmt.Println(users)
}
