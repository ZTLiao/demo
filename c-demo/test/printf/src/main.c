#include <stdio.h>

int my_printf(const char *, ...);

int main(int argc, char* argv[]) {
	int ret = printf("hello,world!\n");
	my_printf("ret = %d\n", ret);
	my_printf("num1 = %d, num2 = %d, num3 = %d, num4 = %d, num5 = %d, num6 = %d, str7 = %s\n", 1, 2, 3, 4, 5, 6, "hello,world!");
	return 0;
}
