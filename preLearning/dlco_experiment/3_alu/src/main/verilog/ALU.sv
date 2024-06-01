// Generated by CIRCT firtool-1.62.0
module ALU(
  input  [7:0] io_a,
               io_b,
  input  [2:0] io_op,
  output [7:0] io_out,
  output       io_overflow,
               io_carry
);

  wire [7:0]      io_out_0;
  wire            _GEN = io_op == 3'h0;
  wire [8:0]      _GEN_0 = {1'h0, io_a};
  wire [8:0]      _GEN_1 = {1'h0, io_b};
  wire [8:0]      sum = _GEN_0 + _GEN_1;
  wire            _GEN_2 = io_op == 3'h1;
  wire [7:0]      _b_T_1 = ~io_b + 8'h1;
  wire [8:0]      sum_1 = _GEN_0 + {1'h0, _b_T_1};
  wire [8:0]      diff = _GEN_0 - _GEN_1;
  wire [7:0][7:0] _GEN_3 =
    {{{7'h0, io_a == io_b}},
     {{7'h0, io_a[7] == io_b[7] ? diff[8] : io_a[7]}},
     {io_a ^ io_b},
     {io_a | io_b},
     {io_a & io_b},
     {~io_a},
     {sum_1[7:0]},
     {sum[7:0]}};
  assign io_out_0 = _GEN_3[io_op];
  assign io_out = io_out_0;
  assign io_overflow =
    _GEN
      ? io_a[7] == io_b[7] & io_a[7] != io_out_0[7]
      : _GEN_2 & io_a[7] == _b_T_1[7] & io_a[7] != io_out_0[7];
  assign io_carry = _GEN ? sum[8] : _GEN_2 & sum_1[8];
endmodule
