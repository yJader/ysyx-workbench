package coder

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

// 忘记看实验要求, 其实不用写这个
class decoder extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(3.W))
    val out = Output(UInt(8.W))
    val enable = Input(Bool())
  })

  io.out := 0.U // 必须要有默认值
  when(io.enable) {
    for (i <- 0 until 8) { // 低位优先
      when(io.in === i.U) {
        io.out := (1 << i).U(8.W)
      }
    }
  }
}
