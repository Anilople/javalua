package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import com.github.anilople.javalua.util.SpiUtils;

public interface LuaNumber extends LuaValue {

  LuaNumber ZERO = newLuaNumber(0D);

  static LuaNumber newLuaNumber(double javaValue) {
    return SpiUtils.loadOneInterfaceImpl(LuaNumber.class, double.class, javaValue);
  }

  @Override
  default LuaType type() {
    return LuaType.LUA_TNUMBER;
  }

  double getJavaValue();

  boolean isPositive();

  boolean isNaN();

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
