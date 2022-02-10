package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import lombok.Data;

@Data
public class LuaBoolean implements LuaValue {

  static final LuaBoolean TRUE = new LuaBoolean(Boolean.TRUE);
  static final LuaBoolean FALSE = new LuaBoolean(Boolean.FALSE);

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

  private final boolean value;

  private LuaBoolean(boolean value) {
    this.value = value;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TBOOLEAN;
  }

  @Override
  public String toString() {
    return "[" + this.value + "]";
  }
}
