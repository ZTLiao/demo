#include <stdio.h>
#include <setjmp.h>

jmp_buf env;

int f(int n) {
	if (n >= 7) {
		longjmp(env, n);
	}
	printf("Call f(%d)\n", n);
	return f(n + 1);
}

int main() {
	int r = setjmp(env);
	if (r == 0) {
		f(1);
	} else {
		printf("Recursion reaches %d\n", r);
	}
	return 0;
}
