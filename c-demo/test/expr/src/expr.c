#include "expr.h"
#include "stack.h"
#include <stdio.h>
#include <string.h>

int priority(char oper) {
  int i, len;
  for (i = 0, len = strlen(OPERATOR); i < len; i++) {
	if (memcmp(&oper, OPERATOR + i, 1) == 0) {
	  return i;
	}
  }
  return 0;
}

int my_atoi(char *buff) {
  int len = strlen(buff);
  int res, i, lim;
  for (i = 0, res = 0, lim = 1; i < len; i++) {
	char c = buff[len - 1 - i];
	if ('0' <= c && c <= '9') {
	  res += (c - 48) * lim; 
      lim = lim * 10;
	}
  }
  return *buff == '-' ? -res : res;
}

int my_itoa(int num, char *buff) {
  if (num == 0) {
    *buff++ = '0';
	return 1;
  }
  int len = 0;
  if (num < 0) {
    num = -num;
	*buff++ = '-';
	len++;
  }
  int tmp = 9;
  int i, lim;
  for (i = tmp, lim = 1; i > 0; i--, lim *= 10);
  for (i = tmp; num > 0 && i >= 0 && lim > 0; i--) {
    int c = num / lim;
    if (c > 0) {
	  *buff++ = (char) (c + '0');
	  num -= c * lim;
	  len++;
	}
	lim = lim / 10;
  }
  buff[len] = 0;
  return len;
}

int compute(char oper, int num1, int num2) {
  int res = 0;
  switch(oper) {
    case '!':
	  res = !num1;
	  break;
	case '*':
	  res = num1 * num2;
	  break;
	case '/':
	  res = num1 / num2;
	  break;
	case '+':
	  res = num1 + num2;
	  break;
	case '-':
	  res = num1 - num2;
	  break;
	case '~':
	  res = ~num1;
	  break;
	case '%':
	  res = num1 % num2;
	  break;
	case '&':
	  res = num1 & num2;
	  break;
	case '^':
	  res = num1 ^ num2;
	  break;
	case '|':
	  res = num1 | num2;
	  break;
	default:
	  printf("no operate!");
  }
  return res;
}

int calc(char *expr) {
  PStack num_stack = Stack__constructor__();
  PStack oper_stack = Stack__constructor__();
  while (*expr != '\0' && expr != NULL) {
    char buff[11] = {0};
	int i;
	if (*expr == '(') {
	  expr++;
	  int j;
      for (i = 0, j = 0; *expr != ')' || j != 0; i++) {
	    if (*expr == '(') {
		  j++;
		} else if (*expr == ')') {
		  j--;
		}
		buff[i] = *expr++;
	  }
	  char *tmp = expr;
	  while (*tmp != '\0') {
	    *tmp = *(tmp + 1);
		tmp++;
	  }
	  printf("expr = %s, buff = %s\n", expr, buff);
	  int val = calc(buff);
	  printf("val = %d\n", val);
	  int len = sizeof(buff);
	  for (i = 0; i < len; i++) {
	    buff[i] = 0;
	  }
	  my_itoa(val, buff);
	} else {
	  for (i = 0; '0' <= *expr && *expr <= '9'; i++) {
	    buff[i] = *expr++;
	  }
	}
	int len = strlen(buff);
	printf("expr buff = %s, len = %d\n", buff, len);
	if (len > 0) {
	  num_stack->push(num_stack, buff, len);
	} else if (*expr != '\0') {
	  char *oper1 = expr++;
	  printf("oper1 = %c\n", *oper1);
	  char *first = oper_stack->first(oper_stack);
	  if (first != NULL) {
		int prior1 = priority(*oper1);
		int prior2 = priority(*first);
	    // printf("prior1 = %d, prior2 = %d\n", prior1, prior2);
		if (prior2 <= prior1) {
		  char oper2[1];
		  oper_stack->pop(oper_stack, oper2);
		  char num2[11];
		  char num1[11];
		  num_stack->pop(num_stack, num2);
		  num_stack->pop(num_stack, num1);
		  int n1 = my_atoi(num1);
		  int n2 = my_atoi(num2);
          int res = compute(*oper2, n1, n2);
	      char tmpbuff[12] = {0};
		  int len = my_itoa(res, tmpbuff);
		  // printf("res = %d, tmpbuff= %s\n", res, tmpbuff);
		  num_stack->push(num_stack, tmpbuff, len);
		}
	  }
	  oper_stack->push(oper_stack, oper1, 1);
	}
	if (expr == NULL || *expr == '\0') {
	  break;
	}
  }
  // printf("before oper_stack->size(oper_stack) = %d\n", oper_stack->size(oper_stack));
  // printf("before num_stack->size(num_stack) = %d\n", num_stack->size(num_stack));
  while (oper_stack->size(oper_stack)) {
    char oper[1];
    oper_stack->pop(oper_stack, oper);
	printf("oper = %c\n", *oper);
    char num2[11] = {0};
	char num1[11] = {0};
	num_stack->pop(num_stack, num2);
	if (strcmp(oper, "!") != 0 && strcmp(oper, "~") != 0) {
	  num_stack->pop(num_stack, num1);
    }
	printf("num1 = %s, oper = %c, num2 = %s\n", num1, *oper, num2);
	int n1 = my_atoi(num1);
	int n2 = my_atoi(num2);
	int res = compute(*oper, n1, n2);
	char tmpbuff[12] = {0};
	int len = my_itoa(res, tmpbuff);
    num_stack->push(num_stack, tmpbuff, len);
  }
  // printf("after oper_stack->size(oper_stack) = %d\n", oper_stack->size(oper_stack));
  // printf("after num_stack->size(num_stack) = %d\n", num_stack->size(num_stack));
  if (num_stack->size(num_stack) == 1) {
    char res[12] = {0};
	num_stack->pop(num_stack, res);
    return my_atoi(res);
  }
  Stack__destructor__(num_stack);
  Stack__destructor__(oper_stack);
  return 0;
}
