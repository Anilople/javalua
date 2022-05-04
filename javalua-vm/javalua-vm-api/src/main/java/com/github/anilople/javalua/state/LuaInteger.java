package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import com.github.anilople.javalua.util.SpiUtils;

public interface LuaInteger extends LuaValue {

  LuaInteger ZERO = LuaInteger.newLuaInteger(0L);

  static LuaInteger newLuaInteger(long javaValue) {
    return SpiUtils.loadOneInterfaceImpl(LuaInteger.class, long.class, javaValue);
  }

  /**
   * 浮点数转为整数，如果小数部分为0，并且整数部分没有超出Lua整数能够表示的范围，则转换成功
   */
  static Return2<LuaInteger, Boolean> fromLuaNumber(LuaNumber luaNumber) {
    return luaNumber.toLuaInteger();
  }

  static Return2<LuaInteger, Boolean> from(LuaValue luaValue) {
    if (null == luaValue) {
      throw new IllegalArgumentException("cannot be null");
    }
    if (luaValue instanceof LuaInteger) {
      return new Return2<>((LuaInteger) luaValue, true);
    }
    if (luaValue instanceof LuaNumber) {
      return ((LuaNumber) luaValue).toLuaInteger();
    }
    if (luaValue instanceof LuaString) {
      var r = LuaNumber.from(luaValue);
      if (r.r1) {
        return r.r0.toLuaInteger();
      }
    }
    return new Return2<>(null, false);
  }

  @Override
  default LuaType type() {
    return LuaType.LUA_TNUMBER;
  }

  long getJavaValue();

  LuaNumber toLuaNumber();

  LuaString toLuaString();

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
