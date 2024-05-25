package selector

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class SelectorTest extends AnyFreeSpec with Matchers {
  "simple test" in {
    simulate(new Selector()) { c =>
      c.io.in_x0.poke(0.U)
      c.io.in_x1.poke(1.U)
      c.io.in_x2.poke(2.U)
      c.io.in_x3.poke(3.U)

      for (i <- 0 until 4) {
        c.io.sel.poke(i.U)
        c.io.out.expect(i.U)
      }
      println("Test passed!")
    }
  }
}
