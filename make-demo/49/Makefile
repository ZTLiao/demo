#.ONESHELL:
#foo : bar/lose
#	cd $(@D)
#	gobble $(@F) > ../$@

#.ONESHELL:
#SHELL = /usr/bin/perl
#.SHELLFLAGS = -e
#show :
#	@f = qw(a b c);
#	print "@f\n";

#.ONESHELL:
#SHELL = /usr/bin/perl
#.SHELLFLAGS = -e
#show :
	# Make sure "@" is not the first character on the first line
#	@f = qw(a b c);
#	print "@f\n";

.ONESHELL:
foo : bar/lose
	@cd $(@D)
	@gobble $(@F) > ../$@
