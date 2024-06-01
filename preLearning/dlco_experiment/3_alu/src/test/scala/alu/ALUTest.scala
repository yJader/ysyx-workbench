package alu

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

class ALUTest extends AnyFreeSpec with Matchers {

  def test(testCases: Array[Array[UInt]], op: ALUOpcode.Type, c: ALU) = {
    c.io.op.poke(op)
    for (i <- 0 until testCases.length) {
      c.io.a.poke(testCases(i)(0))
      c.io.b.poke(testCases(i)(1))
      c.io.out.expect(testCases(i)(2), f"out error at test$i")
      c.io.overflow.expect(testCases(i)(3)(0), f"overflow error at test$i")
      c.io.carry.expect(testCases(i)(4)(0), f"carry error at test$i")
      print(
        f"${op} test $i:${testCases(i)(0)} + ${testCases(i)(1)} = ${testCases(i)(2)} pass\n"
      )
    }
  }

  "add test" in {
    simulate(new ALU()) { c =>
      val input_op = ALUOpcode.ADD
      val testCases = Array(
        // a, b, out, overflow, carry
        Array(5.U, 6.U, 11.U, false.B, false.B), // 5 + 6 = 11
        Array(0.U, 0.U, 0.U, false.B, false.B), // 0 + 0 = 0
        Array(
          "b11111111".U,
          "b11110000".U,
          "b11101111".U,
          false.B,
          true.B
        ), // -1 + -16 = -17
        Array("b11111111".U, "b11111110".U, "b11111101".U, false.B, true.B),
        Array("b10000000".U, "b10000000".U, "b00000000".U, true.B, true.B),
        Array("b01111111".U, "b10000000".U, "b11111111".U, false.B, false.B),
        Array("b11111111".U, 1.U, 0.U, false.B, true.B), // 进位, 但不溢出
        Array("b01111111".U, 1.U, "b10000000".U, true.B, false.B) // 溢出, 但不进位
      )

      test(testCases, input_op, c)
    }
  }

  "sub test" in {
    simulate(new ALU()) { c =>
      val input_op = ALUOpcode.SUB
      val testCases = Array(
        // a, b, out, overflow, carry
        Array(
          5.U, // 00000101
          6.U, // 取反+1: 11111001 + 1 = 11111010
          "b11111111".U,
          false.B,
          false.B
        ), // 5 - 6 = -1
        Array(
          0.U,
          0.U, // 取反+1: (0)11111111 + 1 = (1)00000000
          0.U,
          false.B,
          false.B
        ), // 0 - 0 = 0
        Array(
          "b11111111".U,
          "b11110000".U, // 取反+1: 00001111 + 1 = 00010000
          "b00001111".U, // -1 - -16 = 15
          false.B,
          true.B
        ),
        Array(
          "b11111111".U,
          "b11111110".U, // 取反+1: 00000001 + 1 = 00000010
          1.U, // -1 - -2 = 1
          false.B,
          true.B
        )
      )

      test(testCases, input_op, c)
    }
  }

  "less test" in {
    simulate(new ALU()) { c =>
      val op = ALUOpcode.LESS
      val testCases = Array(
        Array(5.U, 6.U, 1.U, false.B, false.B), // 5 < 6
        Array(6.U, 5.U, 0.U, false.B, false.B), // 6 > 5
        Array("b11111111".U, "b11110000".U, 0.U, false.B, false.B), // -1 > -16
        Array("b11110000".U, "b11111111".U, 1.U, false.B, false.B), // -16 < -1
        Array("b11111111".U, 0.U, 1.U, false.B, false.B), // -1 < 0
        Array(0.U, "b11111111".U, 0.U, false.B, false.B), // 0 > -1
        Array(
          "b10000000".U,
          "b10000000".U,
          0.U, // !-128 < -128
          false.B,
          false.B
        ),
        Array(
          "b01111111".U,
          "b10000000".U,
          0.U, // !127 < -128
          false.B,
          false.B
        ),
        Array("b10000000".U, "b01111111".U, 1.U, false.B, false.B)
      )

      test(testCases, op, c)
    }
  }
}
