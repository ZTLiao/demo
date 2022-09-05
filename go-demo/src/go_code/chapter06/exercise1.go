package exercise1

func Swap(n1 *int, n2 *int) {
	t := *n1
	*n1 = *n2
	*n2 = t
}
