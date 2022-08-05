define two-lines
echo foo
echo $(bar)
endef

foo:
	@echo $(two-lines)

override define two-lines = 
foo
$(bar)
endef
