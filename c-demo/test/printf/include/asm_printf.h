#ifndef __ASM_PRINTF_H__
#define __ASM_PRINTF_H__

typedef char * asm_va_list;

#define _ALIGN(n) ((sizeof(n) + sizeof(long) - 1) & ~(sizeof(long) - 1)) 
#define asm_va_start(ap, v) (ap = ((asm_va_list) &v) + _ALIGN(v))
#define asm_va_arg(ap, t) *((t *)(ap + _ALIGN(t)) - _ALIGN(t))
#define asm_va_end(ap) (ap = (asm_va_list) 0)

int asm_putchar(int c);

int asm_sys_write(int fd, const void *buf, int count);

int asm_printf(const char* template, ...);

#endif
