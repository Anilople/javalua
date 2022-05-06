package com.github.anilople.javalua.instruction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
public class OperandTest {

  @Test
  void Bx() {
    // min
    assertEquals(0, Operand.newOperand(0).Bx());
    // max
    assertEquals(262143, Operand.newOperand(-1).Bx());
  }

  @Test
  void sBx() {
    // min
    assertEquals(-131071, Operand.newOperand(0).sBx());
    // max
    assertEquals(131072, Operand.newOperand(-1).sBx());
  }

  @Test
  void forPrep() {
    // iAsBx模式
    // sBx[31, 14] A[13, 6] Opcode[5, 0]
    var operand = Operand.newOperand(0b100000000000000011_00000001_101000);
    assertEquals(1, operand.A());
    assertEquals(4, operand.sBx());
  }

  @Test
  void iABC_case1() {
    Operand operand = Operand.builder().iABC(7, 8, 9).build();
    assertEquals(7, operand.B());
    assertEquals(8, operand.C());
    assertEquals(9, operand.A());
  }

  @Test
  void iABC_case2() {
    Operand operand = Operand.builder().iABC(1, 1, 1).build();
    assertEquals(1, operand.B());
    assertEquals(1, operand.C());
    assertEquals(1, operand.A());
  }

  @Test
  void iABC_case3() {
    Operand operand = Operand.builder().iABC(8, 8, 8).build();
    assertEquals(8, operand.B());
    assertEquals(8, operand.C());
    assertEquals(8, operand.A());
  }
}
