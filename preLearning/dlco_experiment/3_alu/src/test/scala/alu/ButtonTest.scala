package alu

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import coder.BCD7SegmentCodes
import coder.bcd7seg

class ButtonTest extends AnyFreeSpec with Matchers {
  import alu.Button
  "basic test" in {
    simulate(new Button()) { c =>
      for (i <- 0 until 7) {
        c.io.in.up.poke(true.B)
        c.clock.step(1)
        // 累加三个opcode
        val op0 = c.io.out.peek().litValue
        print(f"i: $i, op: $op0\n")
      }
    }
  }

  "with bcd7seg test" in {
    simulate(new Module {
      val io = IO(new Bundle {
        val in = Input(new Bundle {
          val up = Bool()
          val down = Bool()
          val left = Bool()
          val right = Bool()
        })
        val seg = Output(UInt(3.W))
        val out = Output(UInt(8.W))
      })
      val button = Module(new Button)
      val seg = Module(new coder.bcd7seg)
      button.io.in := io.in
      seg.io.bcd := button.io.out
      seg.io.enable := true.B
      io.out := seg.io.out
      io.seg := button.io.out
    }) { c =>
      println(f"${4.U == 4.U} ${4.U.litValue == 4.U.litValue} ")

      for (i <- 0 until 8) {
        c.io.in.up.poke(true.B)
        // 累加三个opcode
        val seg = c.io.seg.peek()
        val op0 = c.io.out.peek().litValue.intValue.toBinaryString
        val op1 = c.io.out.peek()
        print(
          f"i: $i,$seg op: $op0 f\t=>\t$op1 ${BCD7SegmentCodes.getNum(c.io.out.peek())}\n"
        )
        c.clock.step(1)
      }
    }
  }
}
