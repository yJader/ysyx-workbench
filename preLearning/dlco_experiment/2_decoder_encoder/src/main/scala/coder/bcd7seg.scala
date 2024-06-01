package coder

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

object BCD7SegmentCodes {
  val CODE_ERROR = "b11111101".U(8.W)
  val CODE_NUM_0 = "b00000011".U(8.W)
  val CODE_NUM_1 = "b10011111".U(8.W)
  val CODE_NUM_2 = "b00100101".U(8.W)
  val CODE_NUM_3 = "b00001101".U(8.W)
  val CODE_NUM_4 = "b10011001".U(8.W)
  val CODE_NUM_5 = "b01001001".U(8.W)
  val CODE_NUM_6 = "b01000001".U(8.W)
  val CODE_NUM_7 = "b00011111".U(8.W)
  val CODE_NUM_8 = "b00000001".U(8.W)
  val CODE_NUM_9 = "b00001001".U(8.W)

  val codeSeq = Seq(
    CODE_NUM_0,
    CODE_NUM_1,
    CODE_NUM_2,
    CODE_NUM_3,
    CODE_NUM_4,
    CODE_NUM_5,
    CODE_NUM_6,
    CODE_NUM_7,
    CODE_NUM_8,
    CODE_NUM_9
  )
  var codeMap: Map[Int, Int] = Map[Int, Int]()
  for (i <- 0 until 10) {
    codeMap += (codeSeq(i).litValue.intValue -> i)
  }

  // 将integer转换为BCD7SegmentCodes
  def getCode(num: Int): UInt = {
    num match {
      case 0 => CODE_NUM_0
      case 1 => CODE_NUM_1
      case 2 => CODE_NUM_2
      case 3 => CODE_NUM_3
      case 4 => CODE_NUM_4
      case 5 => CODE_NUM_5
      case 6 => CODE_NUM_6
      case 7 => CODE_NUM_7
      case 8 => CODE_NUM_8
      case 9 => CODE_NUM_9
      case _ => CODE_ERROR
    }
  }

  def getNum(code: UInt): Int = {
    codeMap.getOrElse(code.litValue.intValue, -1)
  }
}

class bcd7seg extends Module {
  import BCD7SegmentCodes._
  val io = IO(new Bundle {
    val bcd = Input(UInt(4.W))
    val out = Output(UInt(8.W))
    val enable = Input(Bool())
  })

  io.out := CODE_ERROR
  when(io.enable) {
    io.out := MuxLookup(io.bcd, CODE_ERROR)(
      Seq(
        0.U -> CODE_NUM_0,
        1.U -> CODE_NUM_1,
        2.U -> CODE_NUM_2,
        3.U -> CODE_NUM_3,
        4.U -> CODE_NUM_4,
        5.U -> CODE_NUM_5,
        6.U -> CODE_NUM_6,
        7.U -> CODE_NUM_7,
        8.U -> CODE_NUM_8,
        9.U -> CODE_NUM_9
      )
    )
  }

}
