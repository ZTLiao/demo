foo := a.o b.o l.a c.o
#bar := $(foo:.o=.c)
bar := $(foo:%.o=%.c)

bar:
	@echo $(bar)
