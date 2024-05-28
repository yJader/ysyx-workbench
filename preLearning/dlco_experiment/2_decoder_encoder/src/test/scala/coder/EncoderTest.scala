package coder

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class EncoderTest extends AnyFreeSpec with Matchers {
  "encoder basic test" in {
    simulate(new encoder()) { c =>
      for (i <- 0 until 8) {
        // 创建一个第i位为1的UInt (用scala的位移运算符 <<)
        val in = (1 << i).U(8.W)
        c.io.enable.poke(true.B)
        c.io.in.poke(in)
        c.io.out.expect(i.U(3.W))
        print(
          "in " + "i=" + i + " : " + in + "(" + (1 << i).toBinaryString + ")"
        )
        println("\t out: " + c.io.out.peek())
      }
      println("Encoder basic test passed!")
    }
  }

  "encoder priority test" in {
    simulate(new encoder()) { c =>
      c.io.enable.poke(true.B)

      // 0x88 = 1000 1000
      c.io.in.poke(0x88.U)
      c.io.out.expect(7.U)

      // 0x44 = 0100 0100
      c.io.in.poke(0x44.U)
      c.io.out.expect(6.U)
      // 0xff = 1111 1111
      c.io.in.poke(0xff.U)
      c.io.out.expect(7.U)

      println("Encoder priority test passed!")
    }
  }
}
