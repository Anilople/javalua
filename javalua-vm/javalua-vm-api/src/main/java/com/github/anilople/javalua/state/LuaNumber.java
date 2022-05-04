package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import com.github.anilople.javalua.util.SpiUtils;

public interface LuaNumber extends LuaValue {

  LuaNumber ZERO = newLuaNumber(0D);

  static LuaNumber newLuaNumber(double javaValue) {
    return SpiUtils.loadOneInterfaceImpl(LuaNumber.class, double.class, javaValue);
  }

  static Return2<LuaNumber, Boolean> from(LuaValue luaValue) {
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
    return new Return2<>(null, false);
  }

  @Override
  default LuaType type() {
    return LuaType.LUA_TNUMBER;
  }

  double getJavaValue();

  boolean isPositive();

  boolean isNaN();

  /**
   * 浮点数转为整数，如果小数部分为0，并且整数部分没有超出Lua整数能够表示的范围，则转换成功
   */
  Return2<LuaInteger, Boolean> toLuaInteger();

  LuaNumber add(LuaNumber luaNumber);

  LuaNumber sub(LuaNumber luaNumber);

  LuaNumber sub();

  LuaNumber multiply(LuaNumber luaNumber);

  LuaNumber division(LuaNumber luaNumber);

  LuaNumber floorDivision(LuaNumber luaNumber);

  LuaNumber pow(LuaNumber exponent);

  boolean lessThen(LuaNumber luaNumber);

  LuaString toLuaString();
}
