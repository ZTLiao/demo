.PHONY: clean

%: %.o
	@echo $@
	@$(CC) $(CFLAGS) -c -o $@ $<

clean:
	-rm -rf *_test *.o *.s
