package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
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

  public void init(double javaValue) {
    this.javaValue = javaValue;
  }

  public double getJavaValue() {
    return this.javaValue;
  }

  public boolean isPositive() {
    return javaValue > 0;
  }

  public boolean isNaN() {
    return Double.isNaN(this.javaValue);
  }

  /**
   * 浮点数转为整数，如果小数部分为0，并且整数部分没有超出Lua整数能够表示的范围，则转换成功
   */
  public Return2<LuaInteger, Boolean> toLuaInteger() {
    long value = (long) this.javaValue;
    boolean success = (double) value == this.javaValue;
    if (success) {
      return new Return2<>(LuaInteger.newLuaInteger(value), success);
    } else {
      return new Return2<>(null, false);
    }
  }

  public LuaNumber add(LuaNumber luaNumber) {
    var value = this.javaValue + luaNumber.getJavaValue();
    return LuaNumber.newLuaNumber(value);
  }

  public LuaNumber sub(LuaNumber luaNumber) {
    var value = this.javaValue - luaNumber.getJavaValue();
    return LuaNumber.newLuaNumber(value);
  }

  public LuaNumber sub() {
    var value = -this.javaValue;
    return LuaNumber.newLuaNumber(value);
  }

  public LuaNumber multiply(LuaNumber luaNumber) {
    var value = this.javaValue * luaNumber.getJavaValue();
    return LuaNumber.newLuaNumber(value);
  }

  public LuaNumber division(LuaNumber luaNumber) {
    var value = this.javaValue / luaNumber.getJavaValue();
    return LuaNumber.newLuaNumber(value);
  }

  public LuaNumber floorDivision(LuaNumber luaNumber) {
    var temp = this.javaValue / luaNumber.getJavaValue();
    var value = Math.floor(temp);
    return LuaNumber.newLuaNumber(value);
  }

  public LuaNumber pow(LuaNumber exponent) {
    var value = Math.pow(this.javaValue, exponent.getJavaValue());
    return LuaNumber.newLuaNumber(value);
  }

  public boolean lessThen(LuaNumber luaNumber) {
    return this.javaValue < luaNumber.getJavaValue();
  }

  public LuaString toLuaString() {
    return LuaString.newLuaString(Double.toString(this.javaValue));
  }
}
