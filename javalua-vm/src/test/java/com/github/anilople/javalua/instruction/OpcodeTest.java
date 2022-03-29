package com.github.anilople.javalua.instruction;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.instruction.Instruction.Opcode;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class OpcodeTest {

  @Test
  void count() {
    // 47条指令
    assertEquals(47, Instruction.Opcode.OPCODES.size());
  }

  @Test
  void forPrep() {
    assertEquals(Opcode.FORPREP, Opcode.of(0b10000000_00000000_11000000_01101000));
  }
}
