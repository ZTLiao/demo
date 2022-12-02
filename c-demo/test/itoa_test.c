#include <stdio.h>

int my_itoa(int num, char *buff);

int main(int argc, char *argv[]) {
  char buff[12] = {0};
  int len = my_itoa(1234, buff);
  printf("len = %d, buff = %s\n", len, buff);
  return 0;
}

int my_itoa(int num, char *buff) {
  int len = 0;
  if (num < 0) {
    num = -num;
	*buff++ = '-';
	len++;
  }
  int tmp = 9;
  int i, lim;
  for (i = tmp, lim = 1; i > 0; i--, lim *= 10);
  for (i = tmp; num > 0 && i >= 0 && lim > 0; i--) {
    int c = num / lim;
    if (c > 0) {
	  *buff++ = (char) (c + '0');
	  num -= c * lim;
	  len++;
	}
	lim = lim / 10;
  }
  buff[len] = 0;
  return len;
}
