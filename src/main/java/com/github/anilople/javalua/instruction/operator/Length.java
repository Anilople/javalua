package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaTable;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 长度运算符
 */
public class Length {

  /**
   * 提取字符串或表的长度
   */
  public static LuaInteger length(LuaValue luaValue) {
    if (luaValue instanceof LuaString) {
      LuaString luaString = (LuaString) luaValue;
      var len = luaString.getValue().length();
      return new LuaInteger(len);
    }
    if (luaValue instanceof LuaTable) {
      LuaTable luaTable = (LuaTable) luaValue;
      return luaTable.length();
    }
    throw new IllegalStateException("" + luaValue);
  }
}
