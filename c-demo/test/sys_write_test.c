#include <unistd.h>
#include <sys/syscall.h>
#include <stdio.h>

#define payload(_c) (_c), sizeof((_c))

int main(int argc, char *argv[]) {
	printf("__NR_write = %d\n", __NR_write);
	syscall(__NR_write, STDOUT_FILENO, payload("OHAI! WHAT IS YOUR NAME? "));
	return 0;
}
