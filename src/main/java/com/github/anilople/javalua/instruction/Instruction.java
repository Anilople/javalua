package com.github.anilople.javalua.instruction;

/**
 * 指令
 *
 * 在lua中，用4个byte表示
 *
 * @author wxq
 */
public class Instruction {

  public static Instruction of(int code) {
    Opcode opcode = Opcode.of(code);
    Operand operand = new Operand(code);
    return new Instruction(opcode, operand);
  }

  private final Opcode opcode;
  private final Operand operand;

  private Instruction(Opcode opcode, Operand operand) {
    this.opcode = opcode;
    this.operand = operand;
  }

  public Opcode getOpcode() {
    return opcode;
  }

  public Operand getOperand() {
    return operand;
  }
}
