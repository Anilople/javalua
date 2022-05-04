package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;

public enum LuaBoolean implements LuaValue {
  TRUE,
  FALSE,
  ;

  public static LuaBoolean from(LuaValue luaValue) {
    if (luaValue == null) {
      throw new IllegalArgumentException("cannot be null");
    }
    if (luaValue instanceof LuaNil) {
      return LuaBoolean.FALSE;
    }
    if (luaValue instanceof LuaBoolean) {
      return (LuaBoolean) luaValue;
    }
    return LuaBoolean.FALSE;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TBOOLEAN;
  }

  @Override
  public String toString() {
    if (TRUE.equals(this)) {
      return "true";
    } else if (FALSE.equals(this)) {
      return "false";
    } else {
      throw new IllegalStateException("unknown " + this);
    }
  }
}
