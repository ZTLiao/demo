.SECONDEXPANSION:
ONEVAR = onefile
TWOVAR = twofile
myfile: $(ONEVAR) $$(TWOVAR)

.SECONDEXPANSION:
AVAR = top
onefile: $(AVAR)
twofile: $$(AVAR)
AVAR = bottom

top:
	@echo "top"

bottom:
	@echo "bottom"

.SECONDEXPANSION:
main_OBJS := main.o try.o test.o
lib_OBJS := lib.o api.o

#main lib: $$($$@_OBJS)

main.o: 
	cc -c main.c

try.o:
	cc -c try.c

test.o:
	cc -c test.c

lib.o:
	cc -c lib.c

api.o:
	cc -c api.c

clean:
	rm -rf *.o main

main_SRCS := main.c try.c test.c
lib_SRCS := lib.c api.c

.SECONDEXPANSION:
main lib: $$(patsubst %.c, %.o, $$($$@_SRCS))
