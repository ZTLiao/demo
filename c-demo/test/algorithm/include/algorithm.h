#ifdef __ALGORITHM_H__
#define __ALGORITHM_H__

void print_array(int *array, int length);

void insert_sort(int *A, int length);

void merge(int *A, int p, int q, int r);

void merge_sort(int *A, int p, int r);

void merge_v2(int *A, int p, int q, int r);

void merge_sort_v2(int *A, int p, int r);

int binary_search(int *A, int p, int q, int x);

void insert_sort_v3(int *A, int length);

int check_sums(int *A, int length, int x);

void merge_v3(int *A, int p, int q, int r);

void merge_sort_v3(int *A, int p, int r);

#endif
