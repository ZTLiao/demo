libs_for_gcc = -lgnu
normal_libs = 

foo: $(objects)
ifeq ($(gcc), gcc)
	$(CC) -o foo $(objects) $(libs_for_gcc)
else 
	$(CC) -o foo $(objects) $(normal_libs)
endif
