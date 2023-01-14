#include <stdio.h>

void INSERTION_SORT(int *A, int length);

int main(int argc, char *argv[]) {
  int A[10] = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
  int length = (sizeof(A) / sizeof(int));
  INSERTION_SORT(A, length);
  int i = 0;
  for (i = 0; i < length; i++) {
    printf("A[%d] = %d\n", i, A[i]);
  }
  return 0;
}

void INSERTION_SORT(int *A, int length) {
  int i = 0;
  int j = 0;
  for (j = 2; j < length; j++) {
    int key = A[j];
	i = j - 1;
	while (i > 0 && A[i] > key) {
	  A[i + 1] = A[i];
	  i = i - 1;
	}
	A[i + 1] = key;
  }
}
