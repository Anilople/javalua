package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import com.github.anilople.javalua.util.SpiUtils;

public interface LuaString extends LuaValue {

  LuaString EMPTY = newLuaString("");

  static LuaString newLuaString(String javaValue) {
    LuaString luaString = SpiUtils.loadOneInterfaceImpl(LuaString.class);
    luaString.init(javaValue);
    return luaString;
  }

  static Return2<LuaString, Boolean> from(LuaValue luaValue) {
    if (null == luaValue) {
      throw new IllegalArgumentException("cannot be null");
    }
    if (luaValue instanceof LuaString) {
      return new Return2<>((LuaString) luaValue, true);
    }
    final LuaString luaString;
    if (luaValue instanceof LuaInteger) {
      LuaInteger luaInteger = (LuaInteger) luaValue;
      luaString = luaInteger.toLuaString();
    } else if (luaValue instanceof LuaNumber) {
      LuaNumber luaNumber = (LuaNumber) luaValue;
      luaString = luaNumber.toLuaString();
    } else {
      luaString = EMPTY;
    }
    return new Return2<>(luaString, true);
  }

  void init(String javaValue);

  String getJavaValue();

  @Override
  default LuaType type() {
    return LuaType.LUA_TSTRING;
  }

  LuaBoolean lessThan(LuaString luaString);

  LuaInteger length();

  LuaNumber toLuaNumber();

  LuaString concat(LuaString luaString);
}
