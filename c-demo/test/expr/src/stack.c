#include "stack.h"

PStack Stack__constructor__() {
  PStack pStack = (PStack) malloc(sizeof(Stack));
  pStack->total = 0;
  pStack->push = push;
  pStack->pop = pop;
  pStack->size = size;
  pStack->last = last;
  pStack->first = first;
  return pStack;
}

void Stack__destructor__(PStack self) {
  PNode pNode = self->curr;
  while (pNode != NULL) {
	if (pNode->val != NULL) {
	  free(pNode->val);
	}
    PNode prev = pNode->prev;
	free(pNode);
	pNode = prev;
  }
  self->top = NULL;
  free(self);
}

void push(PStack self, void *val, int n) {
  PNode pNode = (PNode) malloc(sizeof(Node));
  pNode->val = malloc(n);
  pNode->size = n;
  memcpy(pNode->val, val, n);
  pNode->prev = self->curr;
  if (self->top == NULL) {
    self->top = pNode;
  }
  self->curr = pNode;
  self->total++;
}

void pop(PStack self, void *val) {
  PNode pNode = self->curr;
  if (pNode == NULL) {
    return;
  }
  self->curr = pNode->prev;
  self->total--;
  memcpy(val, pNode->val, pNode->size);
  free(pNode->val);
  if (self->total == 0) {
    self->top = NULL;
	self->curr = NULL;
  }
  free(pNode);
}

int size(PStack self) {
  return self->total;
}

void *last(PStack self) {
  PNode pNode = self->top;
  if (pNode == NULL) {
    return NULL;
  }
  return pNode->val;
}

void *first(PStack self) {
  PNode pNode = self->curr;
  if (pNode == NULL) {
    return NULL;
  }
  return pNode->val;
}
