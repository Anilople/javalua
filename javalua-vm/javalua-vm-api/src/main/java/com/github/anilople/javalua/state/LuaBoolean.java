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
  TRUE,
  FALSE,
  ;

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
