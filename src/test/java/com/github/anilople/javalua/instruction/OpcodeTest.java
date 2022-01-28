package com.github.anilople.javalua.instruction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class OpcodeTest {

  @Test
  void count() {
    // 47条指令
    assertEquals(47, Opcode.OPCODES.size());
  }
}
