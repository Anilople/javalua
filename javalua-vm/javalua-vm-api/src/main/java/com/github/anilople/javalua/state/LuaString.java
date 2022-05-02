package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import lombok.Data;

@Data
public class LuaString implements LuaValue {

  public static Return2<LuaString, Boolean> from(LuaValue luaValue) {
    if (null == luaValue) {
      throw new IllegalArgumentException("cannot be null");
    }
    if (luaValue instanceof LuaString) {
      return new Return2<>((LuaString) luaValue, true);
    }
    final LuaString luaString;
    if (luaValue instanceof LuaInteger) {
      LuaInteger luaInteger = (LuaInteger) luaValue;
      luaString = luaInteger.toLuaString();
    } else if (luaValue instanceof LuaNumber) {
      LuaNumber luaNumber = (LuaNumber) luaValue;
      luaString = luaNumber.toLuaString();
    } else {
      luaString = new LuaString("");
    }
    return new Return2<>(luaString, true);
  }

  private final String value;

  public LuaString(String value) {
    this.value = value;
  }

  @Override
  public LuaType type() {
    return LuaType.LUA_TSTRING;
  }

  @Override
  public String toString() {
    return "\"" + this.value + "\"";
  }
}
