#include <stdio.h>

void xor_swap(int *x, int *y){
	*y = *x ^ *y;
	*x = *x ^ *y;
	*y = *x ^ *y;
}

void reserve_array(int a[], int len){
	int left, right = len - 1;
	for(left = 0; left < right; left++, right--){
		xor_swap(&a[left], &a[right]);
		printf("left = %d, right =%d\n", left, right);
		printf("a[%d] = %d, a[%d] = %d\n", left, a[left], right, a[right]);
	}
}

int main(int argv, char *argc[]){
	int a[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	reserve_array(a, sizeof(a) / sizeof(a[0]));
	int i;
	for(i = 0; i < sizeof(a) / sizeof(a[0]); i++){
		printf(" i = %d\n", a[i]);
	}
	return 0;
}
