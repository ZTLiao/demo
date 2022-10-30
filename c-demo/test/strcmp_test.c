#include <stdio.h>

int my_strcmp(const char *, const char *);

int main(int argc, char *argv[]) {
	printf("strcmp = %d\n", my_strcmp("hello12", "hello1"));
	return 0;
}

int my_strcmp(const char *s1, const char *s2) {
	int b = 0;
	while (*s1 !=0 && *s2 != 0 && *(s1++) == *(s2++)) {
		b = 1;
	}
	b = b && (*s1 == *s2);
	return b;
}
