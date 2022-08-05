#include <stdio.h>

void xor_swap(int *x, int *y){
	*y = *x ^ *y;
	*x = *x ^ *y;
	*y = *x ^ *y;
}

int main(int argv, char* argc[]){
	int x = 10;
	int y = 20;
	xor_swap(&x, &y);
	printf("x = %d, y = %d\n", x, y);
	return 0;
}
