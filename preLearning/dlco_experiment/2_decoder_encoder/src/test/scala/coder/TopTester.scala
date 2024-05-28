package coder

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

/** 将top内部模块暴露出来，以便于测试
  */
class TopTester extends Top {
  val test_io = IO(new Bundle {
    val encoder_in = Output(UInt(8.W))
    val encoder_out = Output(UInt(3.W))
  })
  test_io.encoder_in := encoder.io.in
  test_io.encoder_out := encoder.io.out
}
