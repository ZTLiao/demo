#include <stdio.h>

void print_array(int *, int);

void insert_sort(int *, int);

void select_sort_min(int *, int);

void merge_sort(int *, int, int);

void merge_sort_v2(int *, int, int);

int binary_search(int *, int, int, int);

void insert_sort_v3(int *, int);

int main(int argc, char *argv[]) {
  int A[6] = {5, 2, 4, 6, 1, 3};
  int ALength = (sizeof(A) / sizeof(int));
  printf("==== insert sort ====\n");
  insert_sort(A, ALength);
  print_array(A, ALength);
  printf("==== select sort min ====\n");
  int B[6] = {5, 2, 4, 6, 1, 3};
  int BLength = (sizeof(B) / sizeof(int));
  select_sort_min(B, BLength);
  print_array(B, BLength);
  printf("==== merge sort ====\n");
  int C[6] = {5, 2, 4, 6, 1, 3};
  int CLength = (sizeof(C) / sizeof(int));
  merge_sort(C, 0, CLength - 1);
  print_array(C, CLength);
  printf("==== merge sort v2 ====\n");
  int D[6] = {5, 2, 4, 6, 1, 3};
  int DLength = (sizeof(D) / sizeof(int));
  merge_sort_v2(D, 0, DLength - 1);
  print_array(D, DLength);
  printf("==== binary search ====\n");
  int E[6] = {1, 2, 3, 4, 5, 6};
  int ELength = (sizeof(E) / sizeof(int));
  int index = binary_search(E, 0, ELength - 1, 5);
  printf("index = %d\n", index);
  printf("==== insert sort v3 ====\n");
  int F[6] = {1, 2, 3, 4, 5, 6};
  int FLength = (sizeof(F) / sizeof(int));
  insert_sort_v3(F, FLength);
  print_array(F, FLength);
  return 0;
}
