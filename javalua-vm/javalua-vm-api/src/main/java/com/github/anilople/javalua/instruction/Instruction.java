package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 指令
 * <p>
 * 在lua中，用4个byte表示
 *
 * @author wxq
 */
public interface Instruction {

  static Instruction newInstruction(int code) {
    InstructionFactory instructionFactory = InstructionFactory.newInstructionFactory();
    return instructionFactory.getInstructionBy(code);
  }

  static Instruction[] convert(int[] code) {
    final int length = code.length;
    Instruction[] instructions = new Instruction[length];
    for (int i = 0; i < length; i++) {
      int codeValue = code[i];
      Instruction instruction = Instruction.newInstruction(codeValue);
      instructions[i] = instruction;
    }
    return instructions;
  }

  Opcode getOpcode();

  Operand getOperand();

  void applyTo(LuaVM luaVM);
}
