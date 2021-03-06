package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.util.ByteUtils;

/**
 * 操作数
 * <p/>
 * 高26个bit
 * <p/>
 * 注意：Lua虚拟机指令操作数里携带的寄存器索引是从0开始的，而Lua API里的栈索引是从1开始的
 *
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lopcodes.h#L274">lopcodes.h#L274
 * OpArgMask</a>
 */
public final class Operand {

  /**
   * 2^18 - 1 = 262143
   */
  static final int MAXARG_Bx = (1 << 18) - 1;
  /**
   * 262143 / 2 = 131071
   */
  static final int MAXARG_sBx = MAXARG_Bx >> 1;

  private final int codeValue;

  private Operand(int codeValue) {
    this.codeValue = codeValue;
  }

  public int getCodeValue() {
    return this.codeValue;
  }

  public int A() {
    return A(this.codeValue);
  }

  public int B() {
    return B(this.codeValue);
  }

  public int C() {
    return C(this.codeValue);
  }

  /**
   * @return 0 至 262143
   */
  public int Bx() {
    return Bx(this.codeValue);
  }

  /**
   * {@link OpMode#iAsBx}模式下的sBx操作数会被解释成有符号整数，
   * <p/>
   * 其它情况下被解释成无符号整数
   * <p/>
   * page 43 44
   * <p/>
   * Lua虚拟机用偏移二进制码（Offset Binary，也叫作Excess-K）
   *
   * @return -131071 至 131072
   */
  public int sBx() {
    var bx = ByteUtils.getBits(this.codeValue, 14, 18);
    return bx - MAXARG_sBx;
  }

  public int Ax() {
    return Ax(this.codeValue);
  }

  static int A(int code) {
    return ByteUtils.getBits(code, 6, 8);
  }

  static int B(int code) {
    return ByteUtils.getBits(code, 23, 9);
  }

  static int C(int code) {
    return ByteUtils.getBits(code, 14, 9);
  }

  static int Bx(int code) {
    return ByteUtils.getBits(code, 14, 18);
  }

  static int Ax(int code) {
    return ByteUtils.getBits(code, 6, 26);
  }

  static Operand newOperand(int code) {
    return new Operand(code);
  }

  static Operand iABC(int B, int C, int A) {
    int value = B << 23;
    value |= C << 14;
    value |= A << 6;
    return Operand.newOperand(value);
  }
}
