include $(NVBOARD_HOME)/scripts/nvboard.mk

TOPNAME = top
NXDC_FILES = top.nxdc

VERILATOR = verilator
NVBOARD_CFLAGS = -I$(NVBOARD_HOME)/usr/include

# TODO: 为什么定义在SRC_AUTO_BIND之后, 会找不到target, 但test_echo可以打印出值
BUILD_DIR = ./build
OBJ_DIR = $(BUILD_DIR)/obj_dir
BIN = $(BUILD_DIR)/$(TOPNAME)

# constraint file
SRC_AUTO_BIND = $(abspath $(BUILD_DIR)/auto_bind.cpp)
# SRC_AUTO_BIND = /home/jader/ysyx/ysyx-workbench/preLearning/verilator/test_top/build/auto_bind.cpp
$(SRC_AUTO_BIND): $(NXDC_FILES)
	@echo "Generating auto bind file"
	python3 $(NVBOARD_HOME)/scripts/auto_pin_bind.py $^ $@

# project source files
VSRCS = $(shell find $(abspath ./) -name "*.v")
CSRCS = $(shell find $(abspath ./csrc) -name "*.c" -or -name "*.cc" -or -name "*.cpp")
CSRCS += $(SRC_AUTO_BIND)
# 必须用绝对路径 否则verilator在指定输出目录后会找不到文件
# SOURCE_FILES += top.v sim_main.cpp


$(shell mkdir -p $(BUILD_DIR))
$(shell export DISPLAY=:0.0)


test_echo:
	@echo PWD: $(PWD)
	@echo $(NVBOARD_HOME)
	@echo $(NVBOARD_ARCHIVE)
	# @echo $(VSRCS) $(CSRCS)
	@echo $(NXDC_FILES) $(SRC_AUTO_BIND)
	@echo $(addprefix -LDFLAGS , $(LDFLAGS)) $(addprefix -CFLAGS , $(NVBOARD_CFLAGS))


sim: test_echo $(VSRCS) $(CSRCS) $(NVBOARD_ARCHIVE) $(SRC_AUTO_BIND)
	@echo "Building $(BIN)"
	$(VERILATOR) --cc --exe --trace --build -j 0 -Wall $(VSRCS) $(CSRCS) verilated_vcd_c.cpp $(NVBOARD_ARCHIVE) \
		$(addprefix -LDFLAGS , $(LDFLAGS)) $(addprefix -CFLAGS , $(NVBOARD_CFLAGS)) \
		--Mdir $(OBJ_DIR) -o $(abspath $(BIN))

run: sim
	$(BIN)

clean:
	rm -rf $(BUILD_DIR) 

.PHONY: clean sim run