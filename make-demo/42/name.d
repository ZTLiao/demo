sources = foo.c bar.c

include $(sources:.c=.d)
