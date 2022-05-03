package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import java.util.Objects;

public class LuaNumber implements LuaValue {

  static final LuaNumber ZERO = new LuaNumber(0D);

  private static final Return2<LuaNumber, Boolean> ERROR_RETURN = new Return2<>(null, false);

  public static Return2<LuaNumber, Boolean> from(LuaValue luaValue) {
    if (null == luaValue) {
      throw new IllegalArgumentException("cannot be null");
    }
    if (luaValue instanceof LuaNumber) {
      return new Return2<>((LuaNumber) luaValue, true);
    }
    if (luaValue instanceof LuaInteger) {
      LuaInteger luaInteger = (LuaInteger) luaValue;
      LuaNumber luaNumber = luaInteger.toLuaNumber();
      return new Return2<>(luaNumber, true);
    }
    if (luaValue instanceof LuaString) {
      LuaString luaString = (LuaString) luaValue;
      LuaNumber luaNumber = luaString.toLuaNumber();
      return new Return2<>(luaNumber, true);
    }
    return ERROR_RETURN;
  }

  private final double value;

  public LuaNumber(double value) {
    this.value = value;
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
    LuaNumber luaNumber = (LuaNumber) o;
    return Double.compare(luaNumber.value, value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "" + this.value + "";
  }

  public boolean isPositive() {
    return value > 0;
  }

  public boolean isNaN() {
    return Double.isNaN(this.value);
  }

  /**
   * 浮点数转为整数，如果小数部分为0，并且整数部分没有超出Lua整数能够表示的范围，则转换成功
   */
  public Return2<LuaInteger, Boolean> toLuaInteger() {
    long value = (long) this.value;
    boolean success = (double) value == this.value;
    if (success) {
      return new Return2<>(new LuaInteger(value), success);
    } else {
      return new Return2<>(null, false);
    }
  }

  public LuaNumber add(LuaNumber luaNumber) {
    var value = this.value + luaNumber.value;
    return new LuaNumber(value);
  }

  public LuaNumber sub(LuaNumber luaNumber) {
    var value = this.value - luaNumber.value;
    return new LuaNumber(value);
  }

  public LuaNumber sub() {
    var value = -this.value;
    return new LuaNumber(value);
  }

  public LuaNumber multiply(LuaNumber luaNumber) {
    var value = this.value * luaNumber.value;
    return new LuaNumber(value);
  }

  public LuaNumber division(LuaNumber luaNumber) {
    var value = this.value / luaNumber.value;
    return new LuaNumber(value);
  }

  public LuaNumber floorDivision(LuaNumber luaNumber) {
    var temp = this.value / luaNumber.value;
    var value = Math.floor(temp);
    return new LuaNumber(value);
  }

  public LuaNumber pow(LuaNumber exponent) {
    var value = Math.pow(this.value, exponent.value);
    return new LuaNumber(value);
  }

  public boolean lessThen(LuaNumber luaNumber) {
    return this.value < luaNumber.value;
  }

  public LuaString toLuaString() {
    return new LuaString(Double.toString(this.value));
  }

}
