#hash != printf '\043'
#file_list != find . -name '*.c'
#
#test:
#	@echo $(hash) $(shell $(file_list))

hash := $(shell printf '\043')
var := $(shell find . -name "*.c")

test:
	@echo $(var) 
