#include <algorithm.h>
#include <stdio.h>

void print_array(int *array, int length) {
  int i;
  for (i = 0; i < length; i++) {
    printf("array[%d] = %d\n", i, array[i]);
  }
} 

void insert_sort(int *A, int length) {
  int i, j;
  for (j = 1; j < length; j++) {
    int key = A[j];
	i = j - 1;
	while (i >= 0 && A[i] > key) {
	  A[i + 1] = A[i]; 
	  i = i - 1;
	}
	A[i + 1] = key;
  }
}
