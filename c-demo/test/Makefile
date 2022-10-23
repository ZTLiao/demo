.PHONY: clean

CFLAGS := -ggdb

%: %.o
	@echo $@
	@$(CC) $(CFLAGS) -c -o $@ $<

clean:
	-rm -rf *_test *.o *.s
