var1 := 1 2 3
var2 := $(foreach i,$(var1),$(eval $(call func,$(i))))

define func
	@echo test,$(1)
endef

.PHONY: all
all:
	$(call func,$(var1))
	@echo all,$(var2)

