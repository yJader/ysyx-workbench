#include "VTop.h"
#include "verilated.h"
#include "verilated_vcd_c.h"
#include <nvboard.h>

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

void nvboard_bind_all_pins(VTop *top);

int main(int argc, char **argv)
{
    VerilatedContext *contextp = new VerilatedContext;
    contextp->commandArgs(argc, argv);
    VTop *top = new VTop{contextp};

    // nvboard
    nvboard_init();
    nvboard_bind_all_pins(top);

    // Enable wave tracing
    Verilated::traceEverOn(true);
    VerilatedVcdC *tfp = new VerilatedVcdC();
    // top->trace(tfp, 99);
    // tfp->open("sim.vcd");

    while (!contextp->gotFinish())
    {
        contextp->timeInc(1);
        nvboard_update();
        top->eval();

        top->clock = !top->clock;
    }
    nvboard_quit();
    tfp->close();
    delete top;
    delete contextp;
    return 0;
}
