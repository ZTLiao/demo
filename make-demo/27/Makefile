SUBDIRS = foo bar baz

subdirs:
	for dir in $(SUBDIRS); do \
		$(MAKE) -C $$dir; \
	done
