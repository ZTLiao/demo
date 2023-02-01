void print_array(int *, int);

void insert_sort(int *, int);

int main(int argc, char *argv[]) {
  int A[6] = {5, 2, 4, 6, 1, 3};
  insert_sort(A, (sizeof(A) / sizeof(int)));
  print_array(A, (sizeof(A) / sizeof(int)));
  return 0;
}
