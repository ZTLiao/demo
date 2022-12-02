#ifndef __EXPR_H__
#define __EXPR_H__

#define OPERATOR "()!~*/%+-<<>>&^|"

int priority(char oper);

int my_atoi(char *buff);

int my_itoa(int num, char *buff);

int compute(char oper, int num1, int num2);

int calc(char *expr);

#endif
