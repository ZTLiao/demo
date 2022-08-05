#include <stdio.h>
#include <unistd.h>

int main(void){
	printf("start...\n");
	fork();
	printf("test...\n");
	return 0;
}
