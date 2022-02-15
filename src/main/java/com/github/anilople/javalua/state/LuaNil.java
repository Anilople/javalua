package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;

public class LuaNil implements LuaValue {

  static final LuaNil INSTANCE = new LuaNil();

  private LuaNil() {}

  @Override
  public LuaType type() {
    return LuaType.LUA_TNIL;
  }

  @Override
  public String toString() {
    return "nil";
  }
}
