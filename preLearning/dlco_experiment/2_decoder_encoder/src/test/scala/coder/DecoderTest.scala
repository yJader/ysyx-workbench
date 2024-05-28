package coder

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class DecoderTest extends AnyFreeSpec with Matchers {
  "decoder basic test" in {
    simulate(new decoder()) { c =>
      for (i <- 0 until 8) {
        c.io.enable.poke(true.B)
        c.io.in.poke(i.U)
        c.io.out.expect((1 << i).U(8.W))
        println(
          "in " + "i=" + i + " : " + i.U + "(" + (1 << i).toBinaryString + ")" + "\t out: " + c.io.out
            .peek()
        )
      }
      println("Decoder basic test passed!")
    }
  }
}
