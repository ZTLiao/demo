OBJDIR := objdir
OBJS := $(addprefix $(OBJDIR)/, foo.o bar.o baz.o)

$(OBJDIR)/%.o : %.c
	$(COMPILE.c) $(OUTPUT_OPTION) $<

all: $(OBJS)

$(OBJS) : | $(OBJDIR)

$(OBJDIR) :
	mkdir $(OBJDIR)
