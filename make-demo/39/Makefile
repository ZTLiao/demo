objects = foo.o bar.o
foo.o : defs.h
bar.o : defs.h test.h
$(objects) : config.h

extradeps = 
$(objects) : $(extradeps)
