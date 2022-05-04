package com.github.anilople.javalua.state;

import com.github.anilople.javalua.exception.TypeConversionRuntimeException;
import java.util.Objects;

/**
 * @author wxq
 */
public class LuaNumberImpl implements LuaNumber {
  static final LuaNumber ZERO = LuaNumber.newLuaNumber(0D);

  private double javaValue;

  public LuaNumberImpl() {
    throw new UnsupportedOperationException();
  }

  public LuaNumberImpl(double javaValue) {
    this.javaValue = javaValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LuaNumber luaNumber = (LuaNumber) o;
    return Double.compare(luaNumber.getJavaValue(), javaValue) == 0;
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
  public LuaString toLuaString() {
    return LuaString.newLuaString(Double.toString(this.javaValue));
  }

  @Override
  public boolean canConvertToLuaNumber() {
    return true;
  }

  @Override
  public LuaNumber toLuaNumber() {
    return this;
  }

  /**
   * 浮点数转为整数，如果小数部分为0，并且整数部分没有超出Lua整数能够表示的范围，则转换成功
   */
  @Override
  public boolean canConvertToLuaInteger() {
    // todo, 引入cache
    long value = (long) this.javaValue;
    return (double) value == this.javaValue;
  }

  /**
   * 浮点数转为整数，如果小数部分为0，并且整数部分没有超出Lua整数能够表示的范围，则转换成功
   */
  @Override
  public LuaInteger toLuaInteger() {
    // todo, 引入cache
    if (!this.canConvertToLuaInteger()) {
      throw new TypeConversionRuntimeException(this, LuaInteger.class);
    }
    long value = (long) this.javaValue;
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public double getJavaValue() {
    return this.javaValue;
  }

  @Override
  public boolean isPositive() {
    return javaValue > 0;
  }

  @Override
  public boolean isNaN() {
    return Double.isNaN(this.javaValue);
  }

  @Override
  public LuaNumber add(LuaNumber luaNumber) {
    var value = this.javaValue + luaNumber.getJavaValue();
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public LuaNumber sub(LuaNumber luaNumber) {
    var value = this.javaValue - luaNumber.getJavaValue();
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public LuaNumber sub() {
    var value = -this.javaValue;
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public LuaNumber multiply(LuaNumber luaNumber) {
    var value = this.javaValue * luaNumber.getJavaValue();
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public LuaNumber division(LuaNumber luaNumber) {
    var value = this.javaValue / luaNumber.getJavaValue();
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public LuaNumber floorDivision(LuaNumber luaNumber) {
    var temp = this.javaValue / luaNumber.getJavaValue();
    var value = Math.floor(temp);
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public LuaNumber pow(LuaNumber exponent) {
    var value = Math.pow(this.javaValue, exponent.getJavaValue());
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public boolean lessThen(LuaNumber luaNumber) {
    return this.javaValue < luaNumber.getJavaValue();
  }
}
