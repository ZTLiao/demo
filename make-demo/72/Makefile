ALL = $(basename $(notdir $(shell find tests/. -name "*.c")))

STR = $(findstring a,$(ALL))

STR1 = $(wildcard $(A_HOME)/am/include/am.h)

STR2 = $(sort klib am klib)

test:
	echo $(ALL) -  $(STR) - $(STR1) - $(STR2)

foo: foo.o bar.o
	cc -o foo foo.o bar.o $(CFLAGS) $(LDFLAGS)

LIBS = am klib

$(LIBS): %:
	@echo $* $(LIBS) 
