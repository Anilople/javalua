package com.github.anilople.javalua.instruction;

/**
 * @author wxq
 */
public class OperandBuilder {

  private int value;

  public Operand build() {
    return Operand.newOperand(this.value);
  }

  /**
   * @param B 9 bits
   * @param C 9 bits
   * @param A 8 bits
   */
  public OperandBuilder iABC(int B, int C, int A) {
    this.value = B;

    // C need 9 bits
    this.value <<= 9;
    this.value |= C;

    // A need 8 bits
    this.value <<= 8;
    this.value |= A;

    // 修正，让所有数据都落到 高26个bit
    this.value <<= 6;
    return this;
  }

  /**
   * @param Bx 18 bits
   * @param A 8 bits
   */
  public OperandBuilder iABx(int Bx, int A) {
    this.value = Bx;

    // A need 8 bits
    this.value <<= 8;
    this.value |= A;

    // 修正，让所有数据都落到 高26个bit
    this.value <<= 6;
    return this;
  }

  /**
   * @param sBx 18 bits
   * @param A 8 bits
   */
  public OperandBuilder iAsBx(int sBx, int A) {
    this.value = sBx;

    // A need 8 bits
    this.value <<= 8;
    this.value |= A;

    // 修正，让所有数据都落到 高26个bit
    this.value <<= 6;
    return this;
  }

  /**
   * @param Ax 26 bits
   */
  public OperandBuilder iAx(int Ax) {
    this.value = Ax;
    // 修正，让所有数据都落到 高26个bit
    this.value <<= 6;
    return this;
  }
}
