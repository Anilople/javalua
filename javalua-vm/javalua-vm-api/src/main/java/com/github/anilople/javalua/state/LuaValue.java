package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;

public interface LuaValue {
  LuaType type();

  LuaNil NIL = LuaNil.INSTANCE;
  LuaBoolean TRUE = LuaBoolean.TRUE;
  LuaBoolean FALSE = LuaBoolean.FALSE;

  static LuaBoolean of(boolean value) {
    return value ? LuaValue.TRUE : LuaValue.FALSE;
  }

  static LuaInteger of(long value) {
    return LuaInteger.newLuaInteger(value);
  }

  static LuaNumber of(double value) {
    return LuaNumber.newLuaNumber(value);
  }

  static LuaString of(String value) {
    return LuaString.newLuaString(value);
  }
}
