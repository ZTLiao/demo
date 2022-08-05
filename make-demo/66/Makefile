ifeq ($(.DEFAULT_GOAL),)
	$(warning no default goal is set)
endif

.PHONY: foo
foo: ; @echo $@

$(warning default goal is #(.DEFAULT_GOAL))

.DEFAULT_GOAL :=

.PHONY: bar
bar: ; @echo $@

$(warning default goal is $(.DEFAULT_GOAL))

.DEFAULT_GOAL := foo
