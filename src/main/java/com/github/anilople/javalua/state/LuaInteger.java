package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import lombok.Data;

@Data
class LuaInteger implements LuaValue {

  static Return2<LuaInteger, Boolean> from(LuaValue luaValue) {
    if (null == luaValue) {
      throw new IllegalArgumentException("cannot be null");
    }
    if (luaValue instanceof LuaInteger) {
      return new Return2<>((LuaInteger) luaValue, true);
    }
    throw new UnsupportedOperationException("lua value " + luaValue);
  }

  final long value;

  LuaInteger(long value) {
    this.value = value;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TNUMBER;
  }

  @Override
  public String toString() {
    return "[" + this.value + "]";
  }
}
