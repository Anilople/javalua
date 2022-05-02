package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import java.util.Objects;

public class LuaInteger implements LuaValue {

  public static final LuaInteger ZERO = new LuaInteger(0);

  private static final Return2<LuaInteger, Boolean> ERROR_RETURN = new Return2<>(null, false);

  /**
   * 浮点数转为整数，如果小数部分为0，并且整数部分没有超出Lua整数能够表示的范围，则转换成功
   */
  static Return2<LuaInteger, Boolean> fromLuaNumber(LuaNumber luaNumber) {
    return luaNumber.toLuaInteger();
  }

  public static Return2<LuaInteger, Boolean> from(LuaValue luaValue) {
    if (null == luaValue) {
      throw new IllegalArgumentException("cannot be null");
    }
    if (luaValue instanceof LuaInteger) {
      return new Return2<>((LuaInteger) luaValue, true);
    }
    if (luaValue instanceof LuaNumber) {
      return fromLuaNumber((LuaNumber) luaValue);
    }
    if (luaValue instanceof LuaString) {
      var r = LuaNumber.from(luaValue);
      if (r.r1) {
        return fromLuaNumber(r.r0);
      }
    }
    return ERROR_RETURN;
  }

  private final long value;

  public LuaInteger(long value) {
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
    LuaInteger that = (LuaInteger) o;
    return value == that.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "" + this.value + "";
  }

  public long getJavaValue() {
    return this.value;
  }

  public LuaNumber toLuaNumber() {
    double value = (double) this.value;
    return new LuaNumber(value);
  }

  public LuaInteger add(LuaInteger luaInteger) {
    var value = this.value + luaInteger.value;
    return new LuaInteger(value);
  }
  
  public LuaInteger sub(LuaInteger luaInteger) {
    var value = this.value - luaInteger.value;
    return new LuaInteger(value);
  }
  
  public LuaInteger sub() {
    var value = -this.value;
    return new LuaInteger(value);
  }

  public LuaInteger multiply(LuaInteger luaInteger) {
    var value = this.value * luaInteger.value;
    return new LuaInteger(value);
  }

  public LuaInteger floorDivision(LuaInteger luaInteger) {
    var value = Math.floorDiv(this.value, luaInteger.value);
    return new LuaInteger(value);
  }
  
  public LuaInteger and(LuaInteger luaInteger) {
    var value = this.value & luaInteger.value;
    return new LuaInteger(value);
  }

  public LuaInteger or(LuaInteger luaInteger) {
    var value = this.value | luaInteger.value;
    return new LuaInteger(value);
  }

  public LuaInteger xor(LuaInteger luaInteger) {
    var value = this.value ^ luaInteger.value;
    return new LuaInteger(value);
  }
  
  public LuaInteger negate() {
    var value = ~this.value;
    return new LuaInteger(value);
  }

  /**
   * 无符号右移，空出来的比特只是简单地补0
   */
  public LuaInteger shiftRight(LuaInteger n) {
    if (n.value > 0) {
      var value = this.value >>> n.value;
      return new LuaInteger(value);
    } else {
      var toPositiveN = n.sub();
      return shiftLeft(toPositiveN);
    }
  }

  public LuaInteger shiftLeft(LuaInteger n) {
    if (n.value > 0) {
      var value = this.value << n.value;
      return new LuaInteger(value);
    } else {
      var toPositiveN = n.sub();
      return shiftRight(toPositiveN);
    }
  }

  public boolean lessThen(LuaInteger luaInteger) {
    return this.value < luaInteger.value;
  }

  public LuaString toLuaString() {
    return new LuaString(Long.toString(this.value));
  }
}
