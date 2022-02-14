package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.Constant;

public interface LuaValue {
  LuaType type();

  LuaNil NIL = LuaNil.INSTANCE;
  LuaBoolean TRUE = LuaBoolean.TRUE;
  LuaBoolean FALSE = LuaBoolean.FALSE;

  static LuaBoolean of(boolean value) {
    return value ? LuaValue.TRUE : LuaValue.FALSE;
  }

  static LuaInteger of(long value) {
    return new LuaInteger(value);
  }

  static LuaNumber of(double value) {
    return new LuaNumber(value);
  }

  static LuaString of(String value) {
    return new LuaString(value);
  }

  static LuaValue of(Constant constant) {
    if (constant.isLuaBoolean()) {
      var value = constant.getLuaBooleanInJava();
      return LuaValue.of(value);
    }
    if (constant.isLuaInteger()) {
      var value = constant.getLuaIntegerInJava();
      return LuaValue.of(value);
    }
    if (constant.isLuaNumber()) {
      var value = constant.getLuaNumberInJava();
      return LuaValue.of(value);
    }
    if (constant.isLuaString()) {
      var value = constant.getLuaStringInJava();
      return LuaValue.of(value);
    }
    return LuaValue.NIL;
  }
}
