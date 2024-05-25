package selector

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Selector extends Module {
  val io = IO(new Bundle {
    val in_x0 = Input(UInt(2.W))
    val in_x1 = Input(UInt(2.W))
    val in_x2 = Input(UInt(2.W))
    val in_x3 = Input(UInt(2.W))
    val sel = Input(UInt(2.W))
    val out = Output(UInt(2.W))
  })

  io.out := MuxLookup(io.sel, 0.U)(
    Seq(
      0.U -> io.in_x0,
      1.U -> io.in_x1,
      2.U -> io.in_x2,
      3.U -> io.in_x3
    )
  )
}

object Selector extends App {
  emitVerilog(new Selector, args)
  println("Done emitting")
  // ChiselStage.emitSystemVerilogFile(
  //   new Selector,
  //   firtoolOpts = Array(
  //     "-disable-all-randomization",
  //     "-strip-debug-info",
  //     "--target-dir",
  //     "generated"
  //   )
  // )
}
