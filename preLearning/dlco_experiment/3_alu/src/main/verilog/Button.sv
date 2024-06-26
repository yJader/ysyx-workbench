// Generated by CIRCT firtool-1.62.0
module Button(
  input        clock,
               reset,
               io_in_up,
               io_in_down,
               io_in_left,
               io_in_right,
  output [2:0] io_out
);

  reg [2:0] reg_0;
  reg [3:0] prev;
  always @(posedge clock) begin
    if (reset) begin
      reg_0 <= 3'h0;
      prev <= 4'h0;
    end
    else begin
      if (io_in_right & ~(prev[3]))
        reg_0 <= {1'h0, reg_0[2:1]};
      else if (io_in_left & ~(prev[2]))
        reg_0 <= {reg_0[1:0], 1'h0};
      else if (io_in_down & ~(prev[1]))
        reg_0 <= reg_0 - 3'h1;
      else if (io_in_up & ~(prev[0]))
        reg_0 <= reg_0 + 3'h1;
      prev <= {io_in_right, io_in_left, io_in_down, io_in_up};
    end
  end // always @(posedge)
  assign io_out = reg_0;
endmodule

