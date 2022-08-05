text:=$(patsubst %.c,%.o,x.c.c bar.c)

text:
	@echo $(text)

foo := a.o b.o l.a c.o
bar := $(foo:.o=.c)

foo:
	@echo $(bar)
