package alu

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import coder.bcd7seg
import coder.BCD7SegmentCodes

class TopTest extends AnyFreeSpec with Matchers {

  "button test" in {
    simulate(new Top()) { c =>
      for (i <- 0 until 7) {
        c.io.button_in.up.poke(true.B)
        c.clock.step(1)
        // 累加三个op_code
        val op0 = BCD7SegmentCodes.getNum(c.io.op_code(0).peek())
        val op1 = BCD7SegmentCodes.getNum(c.io.op_code(1).peek())
        val op2 = BCD7SegmentCodes.getNum(c.io.op_code(2).peek())

        print(f"i: $i, op: $op2$op1$op0\n")
      }
    }
  }
}
