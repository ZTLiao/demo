#include "expr.h"
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {
  char input[256];
  while (1) {
    printf(">>");
	scanf("%s", input);
    printf("expr = %s\n", input);
	if (strcmp(input, "q") == 0 || strcmp(input, "quit") == 0 || strcmp(input, "exit") == 0) {
	  printf("exit,bye bye!\n");
	  break;
	}
	printf("calc = %d\n", calc(input));
  }
  return 0;
}
