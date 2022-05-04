package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;

/**
 * https://www.lua.org/pil/2.2.html
 * <p/>
 * Conditionals (such as the ones in control structures)
 * consider false and nil as false and anything else as true. Beware that,
 * <p/>
 * unlike some other scripting languages,
 * Lua considers both zero and the empty string as true in conditional tests.
 */
public enum LuaBoolean implements LuaValue {
  TRUE(true),
  FALSE(false),
  ;
  private static final LuaString LUA_STRING_TRUE = LuaString.newLuaString("true");
  private static final LuaString LUA_STRING_FALSE = LuaString.newLuaString("false");

  private final boolean javaValue;

  LuaBoolean(boolean javaValue) {
    this.javaValue = javaValue;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TBOOLEAN;
  }

  @Override
  public LuaString toLuaString() {
    if (this.javaValue) {
      return LUA_STRING_TRUE;
    } else {
      return LUA_STRING_FALSE;
    }
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
