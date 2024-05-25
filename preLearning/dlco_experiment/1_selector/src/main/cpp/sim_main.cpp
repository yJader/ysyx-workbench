#include "VSelector.h"
#include "verilated.h"
#include "verilated_vcd_c.h"
#include <nvboard.h>

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

void nvboard_bind_all_pins(VSelector *top);

int main(int argc, char **argv)
{
    VerilatedContext *contextp = new VerilatedContext;
    contextp->commandArgs(argc, argv);
    VSelector *top = new VSelector{contextp};

    // nvboard
    nvboard_init();
    nvboard_bind_all_pins(top);

    // Enable wave tracing
    Verilated::traceEverOn(true);
    VerilatedVcdC *tfp = new VerilatedVcdC();
    // top->trace(tfp, 99);
    // tfp->open("sim.vcd");

    int in[4], in_sel, out;
    while (!contextp->gotFinish())
    {
        contextp->timeInc(1);
        nvboard_update();
        top->eval();
        // 只在变化时进行判断
        if (top->io_in_x0 != in[0] || top->io_in_x1 != in[1] || top->io_in_x2 != in[2] || top->io_in_x3 != in[3] || top->io_sel != in_sel || top->io_out != out)
        {
            printf("io_in_x0=%d, io_in_x1=%d, io_in_x2=%d, io_in_x3=%d, io_sel=%d, io_out=%d\n", top->io_in_x0, top->io_in_x1, top->io_in_x2, top->io_in_x3, top->io_sel, top->io_out);
            in[0] = top->io_in_x0;
            in[1] = top->io_in_x1;
            in[2] = top->io_in_x2;
            in[3] = top->io_in_x3;
            in_sel = top->io_sel;
            out = top->io_out;
            assert(out == in[in_sel]);
        }
    }
    nvboard_quit();
    tfp->close();
    delete top;
    delete contextp;
    return 0;
}
