#include <stdio.h>

int main(int argv, char* argc[]) {
	double i = 0xFFFFFFFFFFFFFFFF;
	char c = (char)i;
	printf("%d", c);
	return 0;
}
