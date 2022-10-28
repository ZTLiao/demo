#ifndef __MY_PRINTF_H__
#define __MY_PRINTF_H__

typedef struct my_va_list {
	long fmt;
	void *e0;
	int d8;
	int d4;
	void *d0;
	void *c8;
	void *c0;
	void *b8;
	void *b0;
	long rsi;
	long rdx;
	long rcx;
	long r8;
	long r9;
} my_va_list;

#define _ALIGN(n) ((sizeof(n) + sizeof(void *) - 1) & ~(sizeof(void *) - 1)) 
#define my_va_start(ap, v) \
	ap.fmt = (long) v; \
	ap.d8 = 0x8; \
	ap.d4 = 0x30; \
	asm("mov %%rsi,%0": "=m"(ap.rsi)); \
	asm("mov %%rdx,%0": "=m"(ap.rdx)); \
	asm("mov %%rcx,%0": "=m"(ap.rcx)); \
	asm("mov %%r8,%0": "=m"(ap.r8)); \
	asm("mov %%r9,%0": "=m"(ap.r9)); \
	asm("lea 0x10(%%rbp),%%rax; mov %%rax,%0": "=m"(ap.d0)); \
	ap.c8 = &ap.b0
#define my_va_arg(ap, t) \
	(ap.d8 == ap.d4) ? \
		*((t *)((((char *) ap.d0) + _ALIGN(t)) - _ALIGN(t))) \
		: *((t *)(ap.c8 + ap.d8)); \
	if (ap.d8 <= 0x2F) { \
		ap.d8 += 0x8; \
	} else { \
		ap.d0 += 0x8; \
	}
#define my_va_end(ap)

int my_putchar(int c);

int my_sys_write(int fd, const void *buf, int count);

int my_printf(const char* template, ...);

int my_itoa(int n, char *buffer);

#endif
