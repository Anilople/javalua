package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaNumber;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 算术运算符 8个
 *
 * <p>除法和乘方会先把操作数转为浮点数，再计算。
 *
 * <p>其它运算符会先看操作数是否都是整数，
 *
 * <p>如果都是整数，则运算结果也是整数；
 *
 * <p>如果存在非整数，则全部先转为浮点数，再计算，结果也是浮点数。
 */
public class Arithmetic {

  private Arithmetic() {}

  static LuaInteger addLuaInteger(LuaInteger a, LuaInteger b) {
    var value = a.getValue() + b.getValue();
    return new LuaInteger(value);
  }

  static LuaNumber addLuaNumber(LuaNumber a, LuaNumber b) {
    var value = a.getValue() + b.getValue();
    return new LuaNumber(value);
  }

  public static LuaValue add(LuaValue a, LuaValue b) {
    var rOfLuaIntegers = ToLuaIntegerConverter.convert(a, b);
    if (rOfLuaIntegers.r0) {
      return addLuaInteger(rOfLuaIntegers.r1, rOfLuaIntegers.r2);
    }

    var rOfLuaNumbers = ToLuaNumberConverter.convert(a, b);
    if (rOfLuaNumbers.r0) {
      return addLuaNumber(rOfLuaNumbers.r1, rOfLuaNumbers.r2);
    }

    throw new UnsupportedOperationException(a + " add " + b);
  }

  static LuaInteger subLuaInteger(LuaInteger a, LuaInteger b) {
    var value = a.getValue() - b.getValue();
    return new LuaInteger(value);
  }

  static LuaNumber subLuaNumber(LuaNumber a, LuaNumber b) {
    var value = a.getValue() + b.getValue();
    return new LuaNumber(value);
  }

  public static LuaValue sub(LuaValue a, LuaValue b) {
    var rOfLuaIntegers = ToLuaIntegerConverter.convert(a, b);
    if (rOfLuaIntegers.r0) {
      return subLuaInteger(rOfLuaIntegers.r1, rOfLuaIntegers.r2);
    }

    var rOfLuaNumbers = ToLuaNumberConverter.convert(a, b);
    if (rOfLuaNumbers.r0) {
      return subLuaNumber(rOfLuaNumbers.r1, rOfLuaNumbers.r2);
    }

    throw new UnsupportedOperationException(a + " sub " + b);
  }

  static LuaInteger subLuaInteger(LuaInteger a) {
    var value = -a.getValue();
    return new LuaInteger(value);
  }

  static LuaNumber subLuaNumber(LuaNumber a) {
    var value = -a.getValue();
    return new LuaNumber(value);
  }

  /**
   * @param ignore 被忽略的参数
   */
  public static LuaValue unaryMinus(LuaValue a, LuaValue ignore) {
    if (a instanceof LuaInteger) {
      return subLuaInteger((LuaInteger) a);
    }
    if (a instanceof LuaNumber) {
      return subLuaNumber((LuaNumber) a);
    }
    throw new IllegalArgumentException("unary minus of lua value " + a);
  }

  static LuaInteger multiplyLuaInteger(LuaInteger a, LuaInteger b) {
    var value = a.getValue() * b.getValue();
    return new LuaInteger(value);
  }

  static LuaNumber multiplyLuaNumber(LuaNumber a, LuaNumber b) {
    var value = a.getValue() * b.getValue();
    return new LuaNumber(value);
  }

  public static LuaValue multiply(LuaValue a, LuaValue b) {
    var rOfLuaIntegers = ToLuaIntegerConverter.convert(a, b);
    if (rOfLuaIntegers.r0) {
      return multiplyLuaInteger(rOfLuaIntegers.r1, rOfLuaIntegers.r2);
    }

    var rOfLuaNumbers = ToLuaNumberConverter.convert(a, b);
    if (rOfLuaNumbers.r0) {
      return multiplyLuaNumber(rOfLuaNumbers.r1, rOfLuaNumbers.r2);
    }

    throw new UnsupportedOperationException(a + " multiply " + b);
  }

  static LuaNumber divisionLuaNumber(LuaNumber a, LuaNumber b) {
    var value = a.getValue() / b.getValue();
    return new LuaNumber(value);
  }

  /**
   * 除法
   * @see #power(LuaValue, LuaValue)
   */
  public static LuaValue division(LuaValue a, LuaValue b) {
    var rOfLuaNumbers = ToLuaNumberConverter.convert(a, b);
    if (rOfLuaNumbers.r0) {
      return divisionLuaNumber(rOfLuaNumbers.r1, rOfLuaNumbers.r2);
    }
    throw new UnsupportedOperationException(a + " division " + b);
  }

  /**
   * 向负无穷方向取整，不是向0方向
   *
   * <p>5 // 3 -> 1
   *
   * <p>-5 // 3 -> -2 注意不是 -1
   *
   * <p>5 // -3.0 -> -2.0 注意不是-1.0
   *
   * <p>-5.0 // -3.0 -> 1.0 注意不是2.0
   */
  static LuaInteger floorDivisionLuaInteger(LuaInteger a, LuaInteger b) {
    var value = Math.floorDiv(a.getValue(), b.getValue());
    return new LuaInteger(value);
  }

  static LuaNumber floorDivisionLuaNumber(LuaNumber a, LuaNumber b) {
    var temp = a.getValue() / b.getValue();
    var value = Math.floor(temp);
    return new LuaNumber(value);
  }

  public static LuaValue floorDivision(LuaValue a, LuaValue b) {
    var rOfLuaIntegers = ToLuaIntegerConverter.convert(a, b);
    if (rOfLuaIntegers.r0) {
      return floorDivisionLuaInteger(rOfLuaIntegers.r1, rOfLuaIntegers.r2);
    }

    var rOfLuaNumbers = ToLuaNumberConverter.convert(a, b);
    if (rOfLuaNumbers.r0) {
      return floorDivisionLuaNumber(rOfLuaNumbers.r1, rOfLuaNumbers.r2);
    }

    throw new UnsupportedOperationException(a + " floorDivision " + b);
  }

  /**
   * 组合之前的函数
   *
   * <p>a % b == a - ((a // b) * b)
   */
  static LuaInteger moduloLuaInteger(LuaInteger a, LuaInteger b) {
    var aFloorDivisionB = floorDivisionLuaInteger(a, b);
    var rightPart = multiplyLuaInteger(aFloorDivisionB, b);
    return subLuaInteger(a, rightPart);
  }

  static LuaNumber moduleLuaNumber(LuaNumber a, LuaNumber b) {
    var aFloorDivisionB = floorDivisionLuaNumber(a, b);
    var rightPart = multiplyLuaNumber(aFloorDivisionB, b);
    return subLuaNumber(a, rightPart);
  }

  static LuaValue module(LuaValue a, LuaValue b) {
    var rOfLuaIntegers = ToLuaIntegerConverter.convert(a, b);
    if (rOfLuaIntegers.r0) {
      return moduloLuaInteger(rOfLuaIntegers.r1, rOfLuaIntegers.r2);
    }

    var rOfLuaNumbers = ToLuaNumberConverter.convert(a, b);
    if (rOfLuaNumbers.r0) {
      return moduleLuaNumber(rOfLuaNumbers.r1, rOfLuaNumbers.r2);
    }

    throw new UnsupportedOperationException(a + " module " + b);
  }

  public static LuaNumber powerLuaNumber(LuaNumber base, LuaNumber exponent) {
    var value = Math.pow(base.getValue(), exponent.getValue());
    return new LuaNumber(value);
  }

  /**
   * 乘方
   */
  public static LuaNumber power(LuaValue base, LuaValue exponent) {
    var rOfLuaNumbers = ToLuaNumberConverter.convert(base, exponent);
    if (rOfLuaNumbers.r0) {
      return powerLuaNumber(rOfLuaNumbers.r1, rOfLuaNumbers.r2);
    }
    throw new UnsupportedOperationException(base + " power " + exponent);
  }
}
