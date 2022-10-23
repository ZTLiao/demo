package main

import (
	"fmt"
	"strconv"
	"time"
)

func main() {
	now := time.Now()
	fmt.Printf("now =%v, now = %T\n", now, now)

	fmt.Printf("year = %v\n", now.Year())
	fmt.Printf("month = %v\n", now.Month())
	fmt.Printf("date = %v\n", now.Day())
	fmt.Printf("hour = %v\n", now.Hour())
	fmt.Printf("minute = %v\n", now.Minute())
	fmt.Printf("second = %v\n", now.Second())

	dateStr := fmt.Sprintf("%d-%d-%d %d:%d:%d",
		now.Year(),
		now.Month(),
		now.Day(),
		now.Hour(),
		now.Minute(),
		now.Second())
	fmt.Printf("dateStr = %v\n", dateStr)

	nowStr := now.Format("2006-01-02 15:04:05")
	fmt.Printf("nowStr = %v\n", nowStr)
	time.Sleep(time.Microsecond * 100)
	fmt.Printf("unix = %d, unixNano = %d", time.Now().Unix(), time.Now().UnixNano())

	start := time.Now().Unix()
	test03()
	end := time.Now().Unix()
	fmt.Printf("test03 duration : %v", (end - start))
}

func test03() {
	str := ""
	for i := 0; i < 1000; i++ {
		str += "hello" + strconv.Itoa(i)
	}
}
