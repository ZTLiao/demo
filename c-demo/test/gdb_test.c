#include <stdio.h>

int j = 0;

int display(){
	int i = 0;
	for(i = 0; i < 100; i++){
		printf("%d\n", i);
	}
	return i;
}

void readinput(){
	printf("readinput\n");
}

int main(int argv, char *argc[]){
	printf("start test gdb...\n");
	int v = display();
	readinput();
	printf("v=%d\n", v);
	return 0;
}
