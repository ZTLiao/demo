#include "asm_printf.h"

int asm_putchar(int c) {
	unsigned char ch = c;
	int fd = 1;
	if (asm_sys_write(fd, &ch, 1) <= 0) {
		return -1;
	}
	return ch;
}

int asm_sys_write(int fd, const void *buf, int count) {
	long _ret;
	register long _num  __asm__("rax") = 0x1;
	register long _arg1 __asm__("rdi") = (long) (fd);
	register long _arg2 __asm__("rsi") = (long) (buf);
	register long _arg3 __asm__("rdx") = (long) (count);
	__asm__ volatile (
	"syscall\n"
	: "=a"(_ret)
	: "r"(_arg1), "r"(_arg2), "r"(_arg3), "0"(_num)
	: "rcx", "r11", "memory", "cc"
	);
	return (int) _ret;
}

int asm_printf(const char* template, ...) {
	asm_va_list ap;
	asm_va_start(ap, template);
	int a = asm_va_arg(ap, int);
	asm_va_end(ap);
	return a;
}
