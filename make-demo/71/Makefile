foo := $(dir src/foo.c hacks)
foo := $(notdir src/foo.c hacks)
foo := $(suffix src/foo.c src-1.0/bar.c hacks)
foo := $(basename src/foo.c src-1.0/bar hacks)
foo := $(addsuffix .c,foo bar)
foo := $(addprefix src/,foo bar)
foo := $(join a b,.c .o)

foo:
	@echo $(foo)

clean:
	-rm -rf *.o

print: foo.c bar.c
	lpr -p $?
	touch print
