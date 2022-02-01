package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import lombok.Data;

@Data
class LuaNumber implements LuaValue {

  static final LuaNumber ZERO = new LuaNumber(0D);

  private static final Return2<LuaNumber, Boolean> ERROR_RETURN =
      new Return2<>(LuaNumber.ZERO, true);

  static Return2<LuaNumber, Boolean> from(LuaValue luaValue) {
    if (null == luaValue) {
      throw new IllegalArgumentException("cannot be null");
    }
    if (luaValue instanceof LuaNumber) {
      return new Return2<>((LuaNumber) luaValue, true);
    }
    if (luaValue instanceof LuaInteger) {
      LuaInteger luaInteger = (LuaInteger) luaValue;
      double doubleValue = (double) luaInteger.value;
      LuaNumber luaNumber = new LuaNumber(doubleValue);
      return new Return2<>(luaNumber, true);
    }
    return ERROR_RETURN;
  }

  final double value;

  LuaNumber(double value) {
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
