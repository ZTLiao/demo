#include <stdio.h>

char *my_strcat(char *dst, const char *src);

int main(int argc, char *argv[]) {
  char *s0 = "hello";
  char *s1 = ",world!";
  char *s2 = "hello,world!";
  char str[128];
  char *str1 = my_strcat(str, s0);
  printf("str1 = %s\n", str1);
  char *str2 = my_strcat(str1, s1);
  printf("str2 = %s\n", str2);
  return 0;
}

char *my_strcat(char *dst, const char *src) {
  char *tmp = dst;
  while (*dst || *src) {
    if (*dst != '\0') {
	  dst ++;
	}
	if (*dst == '\0' && *src != '\0') {
		*dst++ = *src++;
		*dst = '\0';
	}
  }
  return tmp;
}
