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

void select_sort_min(int *A, int length) {
  int i, j;
  for (i = 0; i < length; i++) {
    int key = A[i];
	int min = i;
	j = i + 1;
	while (j < length) {
	  if (A[j] < A[min]) {
	    min = j;
	  }
	  j = j + 1;
	}
	A[i] = A[min];
	A[min] = key;
  }
}

void merge(int *A, int p, int q, int r) {
  int n1 = q - p + 1;
  int n2 = r - q;
  int i, j, k;
  int L[n1];
  int R[n2];
  for (i = 0; i < n1; i++) {
    L[i] = A[p + i];
  }
  for (j = 0; j < n2; j++) {
    R[j] = A[q + j + 1];
  }
  L[n1] = 0x7FFFFFF;
  R[n2] = 0x7FFFFFF;
  i = 0;
  j = 0;
  for (k = p; k <= r; k++) {
    if (L[i] <= R[j]) {
      A[k] = L[i];
      i = i + 1;
	} else {
      A[k] = R[j];
	  j = j + 1;
	}
  }
}

void merge_sort(int *A, int p, int r) {
  if (p < r) {
    int q = (p + r) / 2;
	merge_sort(A, p, q);
	merge_sort(A, q + 1, r);
	merge(A, p, q, r);
  }
}

void merge_v2(int *A, int p, int q, int r) {
  int n1 = q - p + 1;
  int n2 = r - q;
  int i, j, k;
  int L[n1];
  int R[n2];
  for (i = 0; i < n1; i++) {
    L[i] = A[p + i];
  }
  for (j = 0; j < n2; j++) {
    R[j] = A[q + j + 1];
  }
  i = 0;
  j = 0;
  for (k = p; k <= r; k++) {
    if (i == n1) {
	  A[k] = R[j];
	  j = j + 1;
	  continue;
	} 
	if (j == n2) {
	  A[k] = L[i];
	  i = i + 1;
	  continue;
    }
	if (L[i] <= R[j]) {
	  A[k] = L[i];
	  i = i + 1;
	} else {
	  A[k] = R[j];
	  j = j + 1;
	}
  }
}

void merge_sort_v2(int *A, int p, int r) {
  if (p < r) {
    int q = (p + r) / 2;
	merge_sort_v2(A, p, q);
	merge_sort_v2(A, q + 1, r);
	merge_v2(A, p, q, r);
  }
}

int binary_search(int *A, int p, int q, int x) {
  if (p > q) {
    return -1;
  } else {
	int m = (q + p) / 2;
    if (x < A[m]) {
	  return binary_search(A, p, m - 1, x);
	} else if (x > A[m]) {
	  return binary_search(A, m + 1, q, x);
	} else {
      return m;
	}
  }
}

void insert_sort_v3(int *A, int length) {
  int i, j;
  for (j = 1; j < length; j++) {
    int key = A[j];
    int high = j - 1;
    int low = 0;
	int mid = 0;
    while (low < high) {
      mid = (low + high) / 2;
      if (key == A[mid]) {
        break;
      }
      if (key < A[mid]) {
        high = mid - 1;
      }
      if (key > A[mid]) {
        low = mid + 1;
      }
    }
    for (i = mid; i < j - 1; i++) {
      A[i + 1] = A[i];
    }
    A[mid] = key;
  }
}

int check_sums(int *A, int length, int x) {
  insert_sort(A, length);
  int i;
  for (i = 0; i < length; i++) {
    int index = binary_search(A, 1, A[i] - x, length);
	if (A[i] >= 0 && index) {
	  return 1;
	}
  }
  return 0;
}


void merge_v3(int *A, int p, int q, int r) {
  int n = r - q + 1;
  int t[n];
  int i, j;
  for (i = 0; i < n; i++) {
    t[i] = A[p + i];
  }
}

void merge_sort_v3(int *A, int p, int r) {
  if (p <= r) {
    int q = (p + r) / 2;
    merge_sort_v3(A, p, q);
    merge_sort_v3(A, q + 1, r);
    merge_v3(A, p, q, r);
  }
}

