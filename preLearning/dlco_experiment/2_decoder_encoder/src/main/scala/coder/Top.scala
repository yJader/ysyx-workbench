package coder

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Top extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(8.W))
    val out = Output(UInt(8.W))
    val enable = Input(Bool())
    val encoder_valid = Output(Bool())
    val encoder_out = Output(UInt(3.W))
  })

  val encoder = Module(new encoder())
  val bcd7seg = Module(new bcd7seg())
  encoder.io.enable := io.enable

  encoder.io.in := io.in
  bcd7seg.io.enable := io.enable & encoder.io.valid // 无输入, 数码管显示ERROR
  bcd7seg.io.bcd := Cat(false.B, encoder.io.out) // 拓展到4位
  io.out := bcd7seg.io.out

  io.encoder_valid := encoder.io.valid
  io.encoder_out := encoder.io.out
}
