package com.github.anilople.javalua.instruction;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.instruction.Instruction.Operand;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
public class OperandTest {

  @Test
  void Bx() {
    // min
    assertEquals(0, Operand.of(0).Bx());
    // max
    assertEquals(262143, Operand.of(-1).Bx());
  }

  @Test
  void sBx() {
    // min
    assertEquals(-131071, Operand.of(0).sBx());
    // max
    assertEquals(131072, Operand.of(-1).sBx());
  }

  @Test
  void forPrep() {
    // iAsBx模式
    // sBx[31, 14] A[13, 6] Opcode[5, 0]
    var operand = Operand.of(0b100000000000000011_00000001_101000);
    assertEquals(1, operand.A());
    assertEquals(4, operand.sBx());
  }
}
