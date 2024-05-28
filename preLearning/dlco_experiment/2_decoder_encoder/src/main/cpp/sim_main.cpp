#include "VTop.h"
#include "verilated.h"
#include "verilated_vcd_c.h"
#include <nvboard.h>

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

void nvboard_bind_all_pins(VTop *top);

int in, out;
bool enable;
bool getTopValue(VTop *top)
{
    // (enable != top->io_enable): 使能变化
    // ((!top->io_enable) && (in != top->io_in || out != top->io_out)): 输入输出变化
    if ((enable != top->io_enable) || ((top->io_enable) && (in != top->io_in || out != top->io_out)))
    {
        enable = top->io_enable;
        if (enable)
        {
            in = top->io_in;
            out = top->io_out;
        }
    }
    else
    {
        return false;
    }

    return true;
}

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

        // if (getTopValue(top))
        // {
        //     std::bitset<32> io_in_bits(top->io_in);
        //     std::bitset<32> io_out_bits(top->io_out);
        //     printf("enable=%d,\tio_in: %s,\tio_out: %s\n", top->io_enable, io_in_bits.to_string().c_str(), io_out_bits.to_string().c_str());
        // }

        // assert(top->io_out == top->io_in);
    }
    nvboard_quit();
    tfp->close();
    delete top;
    delete contextp;
    return 0;
}
