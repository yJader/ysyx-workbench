CFLAG = -Wall -g -o0
SRC = ./src
BUILD = ./build

.PHONY: clean 
clean:
	rm -f $(BUILD)/*

$(BUILD)/%: $(SRC)/%.c
	gcc $(CFLAG) $< -o $@

.PHONY: build 
build: $(BUILD)/$(name)

.PHONY: test
test: $(BUILD)/$(name)
	valgrind --leak-check=full --show-leak-kinds=all --track-origins=yes $<

all:
	make ex1
