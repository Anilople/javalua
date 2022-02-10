package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaBoolean;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 逻辑运算符
 *
 * 逻辑与和逻辑或运算符的结果就是操作数之一，并不会转为布尔值
 */
public class Logical {

  public static LuaValue and(LuaValue... luaValues) {
    for (LuaValue luaValue : luaValues) {
      LuaBoolean luaBoolean = LuaBoolean.from(luaValue);
      if (LuaValue.FALSE.equals(luaBoolean)) {
        return luaValue;
      }
    }
    return LuaValue.TRUE;
  }

  public static LuaBoolean or(LuaValue... luaValues) {
    for (LuaValue luaValue : luaValues) {
      LuaBoolean luaBoolean = LuaBoolean.from(luaValue);
      if (LuaValue.TRUE.equals(luaBoolean)) {
        return LuaValue.TRUE;
      }
    }
    return LuaValue.FALSE;
  }

  static LuaBoolean notLuaBoolean(LuaBoolean a) {
    if (LuaValue.TRUE.equals(a)) {
      return LuaValue.FALSE;
    }
    if (LuaValue.FALSE.equals(a)) {
      return LuaValue.TRUE;
    }
    throw new IllegalStateException("unknown " + a);
  }

  public static LuaBoolean not(LuaValue luaValue) {
    LuaBoolean luaBoolean = LuaBoolean.from(luaValue);
    return notLuaBoolean(luaBoolean);
  }
}