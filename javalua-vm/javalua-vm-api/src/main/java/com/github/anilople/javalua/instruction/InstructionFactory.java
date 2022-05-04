package com.github.anilople.javalua.instruction;

/**
 * @author wxq
 */
public interface InstructionFactory {
  Instruction getInstructionBy(int code);
}
