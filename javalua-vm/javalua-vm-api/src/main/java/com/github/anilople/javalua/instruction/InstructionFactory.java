package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.util.SpiUtils;

/**
 * @author wxq
 */
public interface InstructionFactory {

  static InstructionFactory newInstructionFactory() {
    return SpiUtils.loadOneInterfaceImpl(InstructionFactory.class);
  }

  Instruction getInstructionBy(int code);
}
