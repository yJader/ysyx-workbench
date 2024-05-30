package alu

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class ALUTest extends AnyFreeSpec with Matchers {
  "add test" in {
    simulate(new ALU()) { c =>
      c.io.a.poke(1.U)
      c.io.b.poke(2.U)
      c.io.op.poke(ALUOpcode.ADD)
      c.io.out.expect(3.U)
      c.io.overflow.expect(false.B)
      c.io.carry.expect(false.B)

      c.io.a.poke(0xff.U)
      c.io.b.poke(0x01.U)
      c.io.op.poke(ALUOpcode.ADD)
      c.io.out.expect(0x00.U)
      c.io.overflow.expect(false.B)
      c.io.carry.expect(true.B)
      println("add test pass")
    }
  }
}
