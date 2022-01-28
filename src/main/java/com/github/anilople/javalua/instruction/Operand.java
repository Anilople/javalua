package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.util.ByteUtils;

/**
 * 操作数
 * <p>
 * 高26个bit
 *
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lopcodes.h#L274">lopcodes.h#L274
 * OpArgMask</a>
 */
public final class Operand {

  private final int codeValue;

  Operand(int codeValue) {
    this.codeValue = codeValue;
  }

  int A() {
    return A(this.codeValue);
  }

  int B() {
    return B(this.codeValue);
  }

  int C() {
    return C(this.codeValue);
  }

  int Bx() {
    return Bx(this.codeValue);
  }

  int sBx() {
    return sBx(this.codeValue);
  }

  int Ax() {
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

  static int sBx(int code) {
    return ByteUtils.getBits(code, 14, 18);
  }

  static int Ax(int code) {
    return ByteUtils.getBits(code, 6, 26);
  }
}
