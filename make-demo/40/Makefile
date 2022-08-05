#objects = foo.o bar.o

#all: $(objects)

#$(objects): %.o: %.c
#	$(CC) -c $(CFLAGS) $< -o $@
	
#files = foo.elc bar.o lose.o
#$(filter %.o, $(files)): %.o: %.c
#	$(CC) -c $(CFLAGS) $< -o $@

bigoutput littleoutput : %output : text.g
	generate text.g -S* > $@
