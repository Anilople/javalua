package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;

public interface LuaValue {
  LuaType type();

  LuaNil NIL = LuaNil.INSTANCE;
  LuaBoolean TRUE = LuaBoolean.TRUE;
  LuaBoolean FALSE = LuaBoolean.FALSE;

  static LuaInteger of(long value) {
    return new LuaInteger(value);
  }

  static LuaNumber of(double value) {
    return new LuaNumber(value);
  }

  static LuaString of(String value) {
    return new LuaString(value);
  }
}
