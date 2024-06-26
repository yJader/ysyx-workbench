// Generated by CIRCT firtool-1.62.0
module Top(
  input        clock,
               reset,
  input  [7:0] io_a,
               io_b,
  input        io_button_in_up,
               io_button_in_down,
               io_button_in_left,
               io_button_in_right,
  output [7:0] io_out,
               io_op_code_0,
               io_op_code_1,
               io_op_code_2,
  output       io_overflow,
               io_carry,
  output [7:0] io_empty_seg_0,
               io_empty_seg_1,
               io_empty_seg_2,
               io_empty_seg_3,
               io_empty_seg_4
);

  wire [2:0] _button_io_out;
  ALU alu (
    .io_a        (io_a),
    .io_b        (io_b),
    .io_op       (_button_io_out),
    .io_out      (io_out),
    .io_overflow (io_overflow),
    .io_carry    (io_carry)
  );
  Button button (
    .clock       (clock),
    .reset       (reset),
    .io_in_up    (io_button_in_up),
    .io_in_down  (io_button_in_down),
    .io_in_left  (io_button_in_left),
    .io_in_right (io_button_in_right),
    .io_out      (_button_io_out)
  );
  bcd7seg bcd7seg (
    .io_bcd ({3'h0, _button_io_out[0]}),
    .io_out (io_op_code_0)
  );
  bcd7seg bcd7seg_1 (
    .io_bcd ({3'h0, _button_io_out[1]}),
    .io_out (io_op_code_1)
  );
  bcd7seg bcd7seg_2 (
    .io_bcd ({3'h0, _button_io_out[2]}),
    .io_out (io_op_code_2)
  );
  assign io_empty_seg_0 = 8'hFF;
  assign io_empty_seg_1 = 8'hFF;
  assign io_empty_seg_2 = 8'hFF;
  assign io_empty_seg_3 = 8'hFF;
  assign io_empty_seg_4 = 8'hFF;
endmodule

