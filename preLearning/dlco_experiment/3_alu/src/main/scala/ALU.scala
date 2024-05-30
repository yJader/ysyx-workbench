package alu

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object ALUOpcode extends ChiselEnum {
  val ADD = Value("b000".U)
  val SUB = Value("b001".U)
  val NOT = Value("b010".U)
  val AND = Value("b011".U)
  val OR = Value("b100".U)
  val XOR = Value("b101".U)
  val LESS = Value("b110".U)
  val EQUAL = Value("b111".U)
}

class ALU extends Module {
  import ALUOpcode._

  val io = IO(new Bundle {
    val a = Input(UInt(8.W))
    val b = Input(UInt(8.W))
    val op = Input(ALUOpcode())
    val out = Output(UInt(8.W))
    val overflow = Output(Bool())
    val carry = Output(Bool())
  })

  // init
  io.out := 0.U
  io.overflow := false.B
  io.carry := false.B

  switch(io.op) {
    is(ADD) {
      val sum = Wire(UInt(9.W))
      sum := io.a +& io.b
      io.out := sum(7, 0)
      // 同号相加才会溢出, 溢出时符号位变化
      io.overflow := (io.a(7) === io.b(7)) && (io.a(7) =/= io.out(7))
      io.carry := sum(8)
    }
  }
}
