#include <stdio.h>
#include <assert.h>

#define uint32_t unsigned int

void func(int argv, char* argc[]) {
	int* p = (int*) argc;
	*p = 9999;
	asm volatile("":::"memory");
	if (*p == 9999) {
		printf("9999");
		return;
	}
	printf("8888");
}

void func01() {
	int cr0 = 5;
	asm volatile("mov %%cr0,%0":"=a"(cr0));
}

void func02() {
	int cr0 = 5;
	asm volatile("mov %%cr0,%0":"+a"(cr0));
}

void func03() {
	int cr0 = 5;
	asm volatile("mov %0,%%cr0"::"a"(cr0));
}

void func04(int argv, char* argc[]) {
	char* sh = (char*) &argc;
	asm volatile("lidt %0"::"m"(sh));
}

void func05(int argv, char* argc[]) {
	char* sh = (char*) &argc;
	asm volatile("lidt %0":"=m"(sh));
}

#define JUST_MOV(foo) asm volatile("mov %0, %%eax"::"g"(foo))

void func06() {
	JUST_MOV(100);
	int foo = 100;
	JUST_MOV(foo);
}

void func07(uint32_t dwSomeValue) {
	uint32_t dwRes;
	asm (
	"bsfl %1,%0"
	: "=r" (dwRes)
	: "r" (dwSomeValue)
	: "cc"
	);
	assert(dwRes > 3);
}

int main(int argv, char* argc[]) {
	func(argv, argc);
	func01();
	func02();
	func03();
	func04(argv, argc);
	func05(argv, argc);
	func06();
	func07(1);
	return 0;
}
