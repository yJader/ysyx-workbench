package alu

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new Top(),
    firtoolOpts = Array.concat(
      Array(
        "-disable-all-randomization",
        "-strip-debug-info",
        "--split-verilog"
      ),
      args
    )
  )
}
