include $(NVBOARD_HOME)/scripts/nvboard.mk

TOPNAME = top
SOURCE_FILES += top.v sim_main.cpp

VERILATOR = verilator
NVBOARD_CFLAGS += -CFLAGS -I$(NVBOARD_HOME)/usr/include

BUILD_DIR = ./build
OBJ_DIR = $(BUILD_DIR)/obj_dir
BIN = $(BUILD_DIR)/$(TOPNAME)

# $(shell mkdir -p $(OBJ_DIR))


test_echo:
	@echo $(NVBOARD_HOME)
	@echo $(NVBOARD_ARCHIVE)
	@echo $(addprefix -LDFLAGS , $(LDFLAGS))

sim: 
	$(VERILATOR) --cc --exe --trace --build -j 0 -Wall $(SOURCE_FILES) verilated_vcd_c.cpp $(NVBOARD_ARCHIVE) \
		$(addprefix -LDFLAGS , $(LDFLAGS)) $(NVBOARD_CFLAGS)

run: sim
	export DISPLAY=:0.0 && ./obj_dir/Vtop

clean:
	rm -rf $(BUILD_DIR) 

.PHONY: clean sim run