#include <stdio.h>
#define JUST_MOV(foo) asm volatile("mov %0,%%eax"::"g"(foo))

void fun() {
	int i, j, k;
	i = 0, j = 0, k = 0;
	asm volatile("mov %%eax,%0\n\tpush %%ebx\n\tpop %1\n\tmov %1,%2":"+a"(i), "=b"(j), "=c"(k));
	printf("i = %d, j = %d, k = %d\n", i, j, k);
}

void fun1() {
	int i, j, k;
	i = 0, j = 0, k = 0;
	asm volatile("mov %0,%1":"=b"(j):"a"(1));
	printf("i = %d, j = %d, k = %d\n", i, j, k);
}

void fun2() {
	char str[32] = "fun2...";
	int i = 0;
	printf("before str = %s\n", str);
	asm volatile("mov %0,%1":"=r"(i):"m"(str));
	printf("after str = %s, i = %x\n", str, i);
}

void fun3() {
	JUST_MOV(100);
	int i = 100;
	JUST_MOV(i);
}

void fun4() {
	int __out = 0;
	int __in1 = 1;
	int __int2 = 2;
	asm volatile("pop %0\n\tmov %1 %%esi\n\tmov %2,%%edi\n\t" : "=a"(__out): "r"(__in1), "r"(__int2));
}

int main(int argv, char* argc[]) {
	//fun();
	//fun1();
	//fun2();
	//fun3();
	fun4();
	return 0;
}
