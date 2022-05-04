package com.github.anilople.javalua.instruction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wxq
 */
class OpcodeTest {

  @Test
  void count() {
    // 47条指令
    assertEquals(47, Opcode.OPCODES.size());
  }

  @Test
  void forPrep() {
    assertEquals(Opcode.FORPREP, Opcode.of(0b10000000_00000000_11000000_01101000));
  }
}
