package alu

import chisel3._
import chisel3.util._
import alu.ALUOpcode._
class Button extends Module {
  val io = IO(new Bundle {
    val in = Input(new Bundle {
      val up = Bool()
      val down = Bool()
      val left = Bool()
      val right = Bool()
    })
    val out = Output(UInt(3.W))
  })

  val reg = RegInit(UInt(3.W), ADD.asUInt)
  val prev = RegInit(0.U(4.W)) // button要按下然后松开才算一次

  when(io.in.up && !prev(0)) {
    reg := reg + 1.U
  }
  when(io.in.down && !prev(1)) {
    reg := reg - 1.U
  }
  when(io.in.left && !prev(2)) {
    reg := reg << 1
  }
  when(io.in.right && !prev(3)) {
    reg := reg >> 1
  }

  prev := Cat(io.in.right, io.in.left, io.in.down, io.in.up)

  io.out := reg
}
