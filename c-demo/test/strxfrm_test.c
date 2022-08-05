#include <stdio.h>
#include <string.h>

int main(int argv, char* argc[]){
	char* str = "1234567890";
	char c[256] = {};
	strxfrm(c, str, 2);
	printf("c=%s\n", c);
	return 0;
}
