package coder

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class bcd7segTest extends AnyFreeSpec with Matchers {
  import BCD7SegmentCodes._

  "UInt test" in {
    val a = 1.U(1.W)
    val b = 1.U(4.W)
    val c = 1.U(2.W)
    val d = 1.U(1.W)
    print(f"${a.getWidth}\n")
    print(f"${a == b}\n")
    print(f"${a == c}\n")
    print(f"${a == d}\n")

    // print(f"${Constants(0.U)}\n")
  }

  "simple test" in {
    simulate(new bcd7seg()) { c =>
      c.io.enable.poke(true.B)
      for (i <- 0 until 10) {
        c.io.bcd.poke(i.U)
        c.io.out.expect(BCD7SegmentCodes.getCode(i))
      }
      println("bcd7seg simple test passed!")
    }
  }

  "special test" in {
    simulate(new bcd7seg()) { c =>
      c.io.enable.poke(false.B)
      c.io.bcd.poke(0.U)
      c.io.out.expect(BCD7SegmentCodes.CODE_ERROR)
      println("bcd7seg special test: enable = false passed!")
      c.io.enable.poke(true.B)
      c.io.bcd.poke(1.U)
      c.io.out.expect(BCD7SegmentCodes.CODE_NUM_1)
      println("bcd7seg special test: in-1 passed!")
      c.io.bcd.poke(4.U)
      c.io.out.expect(BCD7SegmentCodes.CODE_NUM_4)
      println("bcd7seg special test: in-4 passed!")
    }
  }
}
