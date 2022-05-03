package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import java.util.Objects;

/**
 * @author wxq
 */
public class LuaIntegerImpl implements LuaInteger {

  private long value;

  public LuaIntegerImpl() {}

  @Override
  public void init(long javaValue) {
    this.value = javaValue;
  }

  @Override
  public long getJavaValue() {
    return this.value;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TNUMBER;
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
    return value == that.getJavaValue();
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "" + this.value + "";
  }

  @Override
  public LuaNumber toLuaNumber() {
    double value = (double) this.value;
    return LuaNumber.newLuaNumber(value);
  }

  @Override
  public LuaInteger add(LuaInteger luaInteger) {
    var value = this.value + luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger sub(LuaInteger luaInteger) {
    var value = this.value - luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger sub() {
    var value = -this.value;
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger multiply(LuaInteger luaInteger) {
    var value = this.value * luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger floorDivision(LuaInteger luaInteger) {
    var value = Math.floorDiv(this.value, luaInteger.getJavaValue());
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger and(LuaInteger luaInteger) {
    var value = this.value & luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger or(LuaInteger luaInteger) {
    var value = this.value | luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger xor(LuaInteger luaInteger) {
    var value = this.value ^ luaInteger.getJavaValue();
    return LuaInteger.newLuaInteger(value);
  }

  @Override
  public LuaInteger negate() {
    var value = ~this.value;
    return LuaInteger.newLuaInteger(value);
  }

  /**
   * 无符号右移，空出来的比特只是简单地补0
   */
  @Override
  public LuaInteger shiftRight(LuaInteger n) {
    if (n.getJavaValue() > 0) {
      var value = this.value >>> n.getJavaValue();
      return LuaInteger.newLuaInteger(value);
    } else {
      var toPositiveN = n.sub();
      return shiftLeft(toPositiveN);
    }
  }

  @Override
  public LuaInteger shiftLeft(LuaInteger n) {
    if (n.getJavaValue() > 0) {
      var value = this.value << n.getJavaValue();
      return LuaInteger.newLuaInteger(value);
    } else {
      var toPositiveN = n.sub();
      return shiftRight(toPositiveN);
    }
  }

  @Override
  public boolean lessThen(LuaInteger luaInteger) {
    return this.value < luaInteger.getJavaValue();
  }

  @Override
  public LuaString toLuaString() {
    return new LuaString(Long.toString(this.value));
  }
}
