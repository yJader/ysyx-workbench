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
    val a = Input(UInt(8.W)) // 输入当作无符号, 手动处理符号位
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
    is(SUB) {
      val b = Wire(UInt(8.W))
      val sum = Wire(UInt(9.W))
      b := ~io.b + 1.U
      sum := io.a +& b
      io.out := sum(7, 0)
      // 同号相加才会溢出, 溢出时符号位变化
      io.overflow := (io.a(7) === b(7)) && (io.a(7) =/= io.out(7))
      io.carry := sum(8)
    }
    is(NOT) {
      io.out := ~io.a
    }
    is(AND) {
      io.out := io.a & io.b
    }
    is(OR) {
      io.out := io.a | io.b
    }
    is(XOR) {
      io.out := io.a ^ io.b
    }
    is(LESS) {
      val diff = Wire(UInt(9.W))
      diff := io.a -& io.b
      // 同号: 不会溢出, 结果为符号位(0: a - b > 0, 即 a > b; 1: a - b < 0, 即 a < b)
      // 异号: 会溢出, 直接取操作数的符号位(a为正(0), b为负, a > b; a为负, b为正, a < b)
      io.out := Mux(io.a(7) === io.b(7), diff(8), io.a(7))
    }
    is(EQUAL) {
      io.out := Mux(io.a === io.b, 1.U, 0.U)
    }
  }
}
