package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaString;
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
    throw new IllegalStateException("" + luaValue);
  }
}
