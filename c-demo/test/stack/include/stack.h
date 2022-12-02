#ifndef __STACK_H__
#define __STACK_H__

#include <stdlib.h>
#include <string.h>

typedef struct Node {
  struct Node *prev;
  void *val;
  int size;
} Node, *PNode;

typedef struct Stack {
  struct Node *top;
  struct Node *curr;
  int total;
  void (*push)(struct Stack *self, void *val, int n);
  void (*pop)(struct Stack *self, void *val);
  int (*size)(struct Stack *self);
} Stack, *PStack;

PStack Stack__constructor__();

void Stack__destructor__(PStack self);

void push(PStack self, void *val, int n);

void pop(PStack self, void *val);

int size(PStack self);

#endif
