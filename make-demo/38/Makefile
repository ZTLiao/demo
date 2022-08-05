#kbd.o command.o files.o: command.h

#kbd.o: command.h
#command.o: command.h
#files.o: command.h

#bigoutput littleoutput : text.g
#	generate text.g -$(subst output,,$@) > $@

bigoutput : text.g
	generate text.g -big > bigoutput

littleoutput : text.g
	generate text.g -little > littleoutput

test:
	@echo $(subst ee,EE,feet on the street)

baz:
	@echo baz

boz:
	@echo boz

foo bar biz &: baz boz
	echo $^ > foo
	echo $^ > bar
	echo $^ > biz
