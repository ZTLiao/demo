ifdef do_sort
func := sort
else
func := strip
endif

bar := a d b g q c

foo := $($(func) $(bar))

foo:
	@echo $(foo)

dir = foo
$(dir)_sources := $(wildcard $(dir)/*.c)
define $(dir)_print = 
lpr $($(dir)_sources)
endef
