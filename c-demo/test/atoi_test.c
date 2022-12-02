#include <stdio.h>
#include <string.h>

int my_atoi(char *buff);

int main(int argc, char *argv[]) {
  int res = my_atoi("1234");
  printf("res = %d\n", res);
  return 0;
}

int my_atoi(char *buff) {
  int len = strlen(buff);
  int res, i, lim;
  for (i = 0, res = 0, lim = 1; i < len; i++) {
    res += (buff[len - 1 - i] - 48) * lim;
	lim = lim * 10;
  }
  return res;
}

