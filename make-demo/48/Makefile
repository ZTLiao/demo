.ONESHELL:
foo : bar/lose
	cd $(@D)
	gobble $(@F) > ../$@

bar/lose :
	@echo bar/lose
