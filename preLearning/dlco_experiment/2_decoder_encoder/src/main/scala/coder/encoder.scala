package coder

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class encoder extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(8.W))
    val out = Output(UInt(3.W))
    val valid = Output(Bool())
    val enable = Input(Bool())
  })
  val index = Reg(UInt(3.W))

  def powerOfTwo(n: Int): Int = {
    1 << n
  }

  io.out := 0.U // 必须要有默认值
  io.valid := 0.U // 默认为0
  when(io.enable) {
    for (i <- 0 until 8) { // 高位优先(会被覆盖)
      when(io.in(i) === true.B) {
        io.out := i.U
        io.valid := 1.U
      }
    }
  }
}
