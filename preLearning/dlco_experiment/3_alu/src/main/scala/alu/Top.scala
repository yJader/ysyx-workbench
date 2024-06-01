package alu

import coder.bcd7seg
import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

class Top extends Module {
  val alu = Module(new ALU)
  val button = Module(new Button)
  val bcd7segs = Array.fill(3)(Module(new bcd7seg))
  val io = IO(new Bundle {
    val a = Input(UInt(8.W))
    val b = Input(UInt(8.W))

    val button_in = Input(new Bundle {
      val up = Bool()
      val down = Bool()
      val left = Bool()
      val right = Bool()
    })

    val out = Output(UInt(8.W))
    val op_code = Vec(3, Output(UInt(8.W)))
    val overflow = Output(Bool())
    val carry = Output(Bool())

    val empty_seg = Vec(5, Output(UInt(8.W)))
  })

  io.empty_seg := VecInit(Seq.fill(5)("b11111111".U(8.W)))

  // button
  button.io.in := io.button_in
  alu.io.op := ALUOpcode(button.io.out)

  // bcd7seg
  for (i <- 0 until 3) {
    bcd7segs(i).io.bcd := Cat(0.U(3.W), button.io.out(i))
    bcd7segs(i).io.enable := true.B
    io.op_code(i) := bcd7segs(i).io.out
  }

  // ALU
  alu.io.a := io.a
  alu.io.b := io.b
  io.out := alu.io.out
  io.overflow := alu.io.overflow
  io.carry := alu.io.carry
}
