#include <stdio.h>

int main(int argc, char* argv[]) {
	int i;
	int j;
	for (i = 0, j = 0; i < 10; i++, j++) {
		printf("i = %d, j = %d\n", i, j);
	}
	return 0;
}
