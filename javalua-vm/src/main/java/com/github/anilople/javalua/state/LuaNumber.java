package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import lombok.Data;

@Data
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
      double value = (double) luaInteger.value;
      LuaNumber luaNumber = new LuaNumber(value);
      return new Return2<>(luaNumber, true);
    }
    if (luaValue instanceof LuaString) {
      LuaString luaString = (LuaString) luaValue;
      double value = Double.parseDouble(luaString.getValue());
      LuaNumber luaNumber = new LuaNumber(value);
      return new Return2<>(luaNumber, true);
    }
    return ERROR_RETURN;
  }

  final double value;

  public LuaNumber(double value) {
    this.value = value;
  }

  public boolean isPositive() {
    return value > 0;
  }

  public boolean isNaN() {
    return Double.isNaN(this.value);
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TNUMBER;
  }

  @Override
  public String toString() {
    return "" + this.value + "";
  }
}
