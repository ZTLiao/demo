#include "stack.h"
#include <stdio.h>

void test() {
  PStack pStack = Stack__constructor__();
  char *str0 = "hello,world!";
  int i;
  for (i = 0; i < 100; i++) {
    pStack->push(pStack, str0, strlen(str0));
    printf("stack size : %d\n", pStack->size(pStack));
  }
  char str1[64];
  for (i = 0; i < 101; i++) {
    pStack->pop(pStack, str1);
    if (str1 != NULL) {
	  printf("str1 = %s\n", str1);
      printf("stack size : %d\n", pStack->size(pStack));
	}
  }
  Stack__destructor__(pStack);
}

int main(int argc, char *argv[]) {
  test();
  return 0;
}
