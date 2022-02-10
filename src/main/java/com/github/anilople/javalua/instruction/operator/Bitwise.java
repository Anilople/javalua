package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 按位运算符
 */
public class Bitwise {

  static LuaInteger andLuaInteger(LuaInteger a, LuaInteger b) {
    var value = a.getValue() & b.getValue();
    return new LuaInteger(value);
  }

  public static LuaInteger and(LuaValue a, LuaValue b) {
    var r = ToLuaIntegerConverter.convert(a, b);
    if (r.r0) {
      return andLuaInteger(r.r1, r.r2);
    } else {
      throw new UnsupportedOperationException(a + " and " + b);
    }
  }

  static LuaInteger orLuaInteger(LuaInteger a, LuaInteger b) {
    var value = a.getValue() | b.getValue();
    return new LuaInteger(value);
  }

  public static LuaInteger or(LuaValue a, LuaValue b) {
    var r = ToLuaIntegerConverter.convert(a, b);
    if (r.r0) {
      return orLuaInteger(r.r1, r.r2);
    } else {
      throw new UnsupportedOperationException(a + " or " + b);
    }
  }

  static LuaInteger xorLuaInteger(LuaInteger a, LuaInteger b) {
    var value = a.getValue() ^ b.getValue();
    return new LuaInteger(value);
  }

  public static LuaInteger xor(LuaValue a, LuaValue b) {
    var r = ToLuaIntegerConverter.convert(a, b);
    if (r.r0) {
      return xorLuaInteger(r.r1, r.r2);
    } else {
      throw new UnsupportedOperationException(a + " xor " + b);
    }
  }

  static LuaInteger negateLuaInteger(LuaInteger a) {
    var value = ~a.getValue();
    return new LuaInteger(value);
  }

  public static LuaInteger negate(LuaValue a) {
    var r = ToLuaIntegerConverter.convert(a);
    return negateLuaInteger(r);
  }

  /**
   * @param ignore 被忽略的参数
   */
  public static LuaInteger negate(LuaValue a, LuaValue ignore) {
    return negate(a);
  }

  /**
   * 无符号右移，空出来的比特只是简单地补0
   */
  static LuaInteger shiftRightLuaInteger(LuaInteger a, LuaInteger n) {
    if (n.getValue() > 0) {
      var value = a.getValue() >>> n.getValue();
      return new LuaInteger(value);
    } else {
      var toPositiveN = Arithmetic.subLuaInteger(n);
      return shiftLeft(a, toPositiveN);
    }
  }

  /**
   * 无符号右移，空出来的比特只是简单地补0
   */
  public static LuaInteger shiftRight(LuaValue a, LuaValue n) {
    var r = ToLuaIntegerConverter.convert(a, n);
    if (r.r0) {
      return shiftRightLuaInteger(r.r1, r.r2);
    } else {
      throw new UnsupportedOperationException(a + " shiftRight " + n);
    }
  }

  public static LuaInteger shiftLeftLuaInteger(LuaInteger a, LuaInteger n) {
    if (n.getValue() > 0) {
      var value = a.getValue() << n.getValue();
      return new LuaInteger(value);
    } else {
      var toPositiveN = Arithmetic.subLuaInteger(n);
      return shiftRight(a, toPositiveN);
    }
  }

  public static LuaInteger shiftLeft(LuaValue a, LuaValue n) {
    var r = ToLuaIntegerConverter.convert(a, n);
    if (r.r0) {
      return shiftLeftLuaInteger(r.r1, r.r2);
    } else {
      throw new UnsupportedOperationException(a + " shiftLeft " + n);
    }
  }
}
