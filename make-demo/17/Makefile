clean:
	rm -rf *.o

print: *.c
	lpr -p $?
	touch print

objects = *.o

objects := $(wildcard *.o)

print2:
	@echo $(objects)
