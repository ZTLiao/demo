.PHONY: run clean

BUILD_DIR ?= build
BINARY ?= $(BUILD_DIR)/algorithm_test

BASEHOME := $(shell pwd)
INCLUDES := $(BASEHOME)/include
CFLAGS += -O0 -MMD -Wall -Werror -ggdb3 -I$(INCLUDES)
SRCS := $(shell find src/ -name "*.c")
OBJS := $(SRCS:src/%.c=build/%.o)

$(BUILD_DIR)/%.o: src/%.c
	@mkdir -p $(dir $@)
	@echo CC $(CFLAGS) -c -o $@ $<
	@$(CC) $(CFLAGS) -c -o $@ $<

$(BINARY): $(OBJS)
	@echo gcc -O2 -rdynamic -o $@ $^
	@gcc -O2 -rdynamic -o $@ $^

run: $(BINARY)
	-rm -rf $(BUILD_DIR)/*.d
	-rm -rf $(BUILD_DIR)/*.o

clean:
	-rm -rf $(BUILD_DIR)
