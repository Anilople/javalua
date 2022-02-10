package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import lombok.Data;

@Data
public class LuaInteger implements LuaValue {

  private static final LuaInteger ZERO = new LuaInteger(0);

  private static final Return2<LuaInteger, Boolean> ERROR_RETURN = new Return2<>(ZERO, false);

  /**
   * 浮点数转为整数，如果小数部分为0，并且整数部分没有超出Lua整数能够表示的范围，则转换成功
   */
  static Return2<LuaInteger, Boolean> fromLuaNumber(LuaNumber luaNumber) {
    long value = (long) luaNumber.getValue();
    boolean success = (double) value == luaNumber.getValue();
    return new Return2<>(new LuaInteger(value), success);
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
      LuaString luaString = (LuaString) luaValue;
      var value = Long.parseLong(luaString.getValue());
      return new Return2<>(new LuaInteger(value), true);
    }
    return ERROR_RETURN;
  }

  final long value;

  public LuaInteger(long value) {
    this.value = value;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TNUMBER;
  }

  @Override
  public String toString() {
    return "[" + this.value + "]";
  }
}
