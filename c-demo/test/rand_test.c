#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[]){
	srand(3);
	char buf[256] = {};
	int i;
	for(i = 0; i < 100; i++){
		int n = rand() % 9 + 1;
		printf("i = %d\tn = %d\n", i, n);
		buf[i] = (char) n;
	}
	buf[0] = (char)1;
	buf[1] = '2';
	buf[3] = '3';
	buf[100] = '\0';
	printf("buf = %s\n", buf);
	return 0;
}
