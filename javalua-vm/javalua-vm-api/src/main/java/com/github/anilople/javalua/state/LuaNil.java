package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;

public enum LuaNil implements LuaValue {
  INSTANCE,
  ;

  @Override
  public LuaType type() {
    return LuaType.LUA_TNIL;
  }

  @Override
  public String toString() {
    return "nil";
  }
}
