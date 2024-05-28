include $(NVBOARD_HOME)/scripts/nvboard.mk

# 工程描述
TOPNAME = Selector
NXDC_FILES = src/main/verilog/Selector.nxdc


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
CPP_DIR = src/main/cpp
VERILOG_DIR = src/main/verilog
VSRCS = $(abspath $(VERILOG_DIR)/*.sv)
CSRCS = $(abspath $(CPP_DIR))/*.cpp
CSRCS += $(SRC_AUTO_BIND)
# 必须用绝对路径 否则verilator在指定输出目录后会找不到文件
# SOURCE_FILES += top.v sim_main.cpp


$(shell mkdir -p $(BUILD_DIR))
$(shell export DISPLAY=:0.0)


test_echo:
	@echo PWD: $(PWD)
	@echo VERILOG_FILES:\d $(VSRCS)
	@echo $(NXDC_FILES) $(SRC_AUTO_BIND)
	@echo $(addprefix -LDFLAGS , $(LDFLAGS)) $(addprefix -CFLAGS , $(NVBOARD_CFLAGS))

cc: $(VSRCS)
	$(VERILATOR) --cc --build -j 0 $^ --Mdir $(OBJ_DIR)

sim: $(VSRCS) $(CSRCS)
	@echo "Building $(BIN)"
	$(VERILATOR) --cc --exe --trace --build -j 0 -Wall -Wno-UNUSEDSIGNAL $^  verilated_vcd_c.cpp $(NVBOARD_ARCHIVE) \
		$(addprefix -LDFLAGS , $(LDFLAGS)) $(addprefix -CFLAGS , $(NVBOARD_CFLAGS)) \
		--Mdir $(OBJ_DIR) -o $(abspath $(BIN))

run: sim
	export export DISPLAY=:0.0 && $(BIN)

clean:
	rm -rf $(BUILD_DIR) 

.PHONY: clean sim run