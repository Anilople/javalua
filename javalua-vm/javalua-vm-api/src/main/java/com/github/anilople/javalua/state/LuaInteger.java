package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.SpiUtils;

public interface LuaInteger extends LuaValue {

  LuaInteger ZERO = LuaInteger.newLuaInteger(0L);

  static LuaInteger newLuaInteger(long javaValue) {
    return SpiUtils.loadOneInterfaceImpl(LuaInteger.class, long.class, javaValue);
  }

  @Override
  default LuaType type() {
    return LuaType.LUA_TNUMBER;
  }

  long getJavaValue();

  LuaInteger add(LuaInteger luaInteger);

  LuaInteger sub(LuaInteger luaInteger);

  LuaInteger sub();

  LuaInteger multiply(LuaInteger luaInteger);

  LuaInteger floorDivision(LuaInteger luaInteger);

  LuaInteger and(LuaInteger luaInteger);

  LuaInteger or(LuaInteger luaInteger);

  LuaInteger xor(LuaInteger luaInteger);

  LuaInteger negate();

  /**
   * 无符号右移，空出来的比特只是简单地补0
   */
  LuaInteger shiftRight(LuaInteger n);

  LuaInteger shiftLeft(LuaInteger n);

  boolean lessThen(LuaInteger luaInteger);
}
