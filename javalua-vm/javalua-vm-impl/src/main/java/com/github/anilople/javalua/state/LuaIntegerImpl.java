package com.github.anilople.javalua.state;

import java.util.Objects;

/**
 * @author wxq
 */
public class LuaIntegerImpl implements LuaInteger {

  private final long javaValue;

  public LuaIntegerImpl() {
    throw new UnsupportedOperationException();
  }

  public LuaIntegerImpl(long javaValue) {
    this.javaValue = javaValue;
  }

  @Override
  public long getJavaValue() {
    return this.javaValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LuaInteger that = (LuaInteger) o;
    return javaValue == that.getJavaValue();
  }

  @Override
  public int hashCode() {
    return Objects.hash(javaValue);
  }

  @Override
  public String toString() {
    return "" + this.javaValue + "";
  }

  @Override
  public boolean canConvertToLuaNumber() {
    return true;
  }

  @Override
  public LuaNumber toLuaNumber() {
    // todo, 引入cache
    double value = (double) this.javaValue;
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public boolean canConvertToLuaInteger() {
    return true;
  }

  @Override
  public LuaInteger toLuaInteger() {
    return this;
  }

  @Override
  public LuaInteger add(LuaInteger luaInteger) {
    var value = this.javaValue + luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger sub(LuaInteger luaInteger) {
    var value = this.javaValue - luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger sub() {
    var value = -this.javaValue;
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger multiply(LuaInteger luaInteger) {
    var value = this.javaValue * luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger floorDivision(LuaInteger luaInteger) {
    var value = Math.floorDiv(this.javaValue, luaInteger.getJavaValue());
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger and(LuaInteger luaInteger) {
    var value = this.javaValue & luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger or(LuaInteger luaInteger) {
    var value = this.javaValue | luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger xor(LuaInteger luaInteger) {
    var value = this.javaValue ^ luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger negate() {
    var value = ~this.javaValue;
    return LuaInteger.newLuaInteger(value);
  }

  /**
   * 无符号右移，空出来的比特只是简单地补0
   */
  @Override
  public LuaInteger shiftRight(LuaInteger n) {
    if (n.getJavaValue() > 0) {
      var value = this.javaValue >>> n.getJavaValue();
      return LuaInteger.newLuaInteger(value);
    } else {
      var toPositiveN = n.sub();
      return shiftLeft(toPositiveN);
    }
  }

  @Override
  public LuaInteger shiftLeft(LuaInteger n) {
    if (n.getJavaValue() > 0) {
      var value = this.javaValue << n.getJavaValue();
      return LuaInteger.newLuaInteger(value);
    } else {
      var toPositiveN = n.sub();
      return shiftRight(toPositiveN);
    }
  }

  @Override
  public boolean lessThen(LuaInteger luaInteger) {
    return this.javaValue < luaInteger.getJavaValue();
  }

  @Override
  public LuaString toLuaString() {
    return LuaString.newLuaString(Long.toString(this.javaValue));
  }
}
