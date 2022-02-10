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
    var luaIntegers = ToLuaIntegerConverter.convert(a, b);
    return andLuaInteger(luaIntegers.r0, luaIntegers.r1);
  }

  static LuaInteger orLuaInteger(LuaInteger a, LuaInteger b) {
    var value = a.getValue() | b.getValue();
    return new LuaInteger(value);
  }

  public static LuaInteger or(LuaValue a, LuaValue b) {
    var luaIntegers = ToLuaIntegerConverter.convert(a, b);
    return orLuaInteger(luaIntegers.r0, luaIntegers.r1);
  }

  static LuaInteger xorLuaInteger(LuaInteger a, LuaInteger b) {
    var value = a.getValue() ^ b.getValue();
    return new LuaInteger(value);
  }

  public static LuaInteger xor(LuaValue a, LuaValue b) {
    var luaIntegers = ToLuaIntegerConverter.convert(a, b);
    return xorLuaInteger(luaIntegers.r0, luaIntegers.r1);
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
    return shiftRightLuaInteger(r.r0, r.r1);
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
    return shiftLeftLuaInteger(r.r0, r.r1);
  }
}
