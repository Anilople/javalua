package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;

public enum LuaNil implements LuaValue {
  INSTANCE,
  ;

  private static final LuaString LUA_STRING_NIL = LuaString.newLuaString("nil");

  @Override
  public LuaType type() {
    return LuaType.LUA_TNIL;
  }

  @Override
  public LuaString toLuaString() {
    return LUA_STRING_NIL;
  }

  @Override
  public String toString() {
    return "nil";
  }
}
