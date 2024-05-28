package coder

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class TopTest extends AnyFreeSpec with Matchers {
  "basic top test" in {
    simulate(new TopTester()) { c =>
      for (i <- 0 until 8) {
        c.io.enable.poke(true.B)
        c.io.in.poke((1 << i).U)
        // println(
        //   f"in i=$i : ${i.U}(0b${(1 << i).toBinaryString})\t out: ${c.io.out.peek()}"
        // )
        // println(
        //   f"\t encoder: in: ${c.test_io.encoder_in.peek()} out: ${c.test_io.encoder_out.peek()}"
        // )
        c.io.out.expect((1 << i).U(8.W))
      }
      println("Top basic test passed!")
    }
  }

  "priority top test" in {
    simulate(new TopTester()) { c =>
      c.io.enable.poke(true.B)
      // 0x88 10001000
      c.io.in.poke(0x88.U)
      c.io.out.expect(0x80.U)

      // 0x44 01000100
      c.io.in.poke(0x44.U)
      c.io.out.expect(0x40.U)

      // 0x22 00100010
      c.io.in.poke(0x22.U)
      c.io.out.expect(0x20.U)

      // 0xff 11111111
      c.io.in.poke(0xff.U)
      c.io.out.expect(0x80.U)
    }
  }
}
