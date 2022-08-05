define run-yacc = 
yacc $(firstword $^)
mv y.tab.c $@
endef

foo.c : foo.y
	$(run-yacc)
