
sim: 
	verilator --cc --exe --trace --build -j 0 -Wall sim_main.cpp top.v verilated_vcd_c.cpp

run:
	./obj_dir/Vtop

clean:
	rm -rf obj_dir 

.PHONY: clean sim run