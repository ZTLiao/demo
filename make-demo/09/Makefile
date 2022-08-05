immediate = deferred
immediate ?= deferred
immediate := immediate
immediate ::= immediate
immediate += deferred or immediate 
#immediate != immediate

define immediate
	deferred
endef

define immediate =
	deferred
endef

define immediate ?=
	deferred
endef

define immediate :=
	deferred
endef

define immediate ::=
	immediate
endef

define immediate +=
	deferred or immediate
endef

#define immediate !=
#	immediate
#endef

#immediate: immediate ; deferred 
#	deferred

deferred:
	@echo "deferred"

immediate1:
	@echo "immediate1"
