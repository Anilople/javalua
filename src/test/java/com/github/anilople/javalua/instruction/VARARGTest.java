package com.github.anilople.javalua.instruction;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.instruction.Instruction.Opcode;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class VARARGTest {

  /**
   * page 159
   */
  @Test
  void testA1B5() {
    int code = Instruction.Opcode.getOpcodeValueOf(Opcode.VARARG);
    code |= 0b000000101_000000000_00000001_000000;
    Instruction instruction = Instruction.of(code);
    assertTrue(instruction instanceof VARARG);
    assertEquals(5, instruction.getOperand().B());
    assertEquals(1, instruction.getOperand().A());
    // TODO
  }
}
