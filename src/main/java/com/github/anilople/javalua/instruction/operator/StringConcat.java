package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 字符串拼接运算符
 */
public class StringConcat {

  static LuaString concatLuaString(LuaString... luaStrings) {
    var sb = new StringBuilder();
    for (LuaString luaString : luaStrings) {
      sb.append(luaString.getValue());
    }
    var value = sb.toString();
    return new LuaString(value);
  }

  /**
   * @return false 如果无法使用 {@link #concat(LuaValue...)}
   */
  static boolean canConcat(LuaValue luaValue) {
    if (LuaType.LUA_TSTRING.equals(luaValue.type())) {
      return true;
    }
    if (LuaType.LUA_TNUMBER.equals(luaValue.type())) {
      return true;
    }
    return false;
  }

  /**
   * @return false 如果无法使用 {@link #concat(LuaValue...)}
   */
  public static boolean canConcat(LuaValue... luaValues) {
    for (LuaValue luaValue : luaValues) {
      if (!canConcat(luaValue)) {
        return false;
      }
    }
    return true;
  }

  public static LuaString concat(LuaValue... luaValues) {
    LuaString[] luaStrings = new LuaString[luaValues.length];
    for (int i = 0; i < luaValues.length; i++) {
      LuaValue luaValue = luaValues[i];
      LuaString luaString = ToLuaStringConverter.convert(luaValue);
      luaStrings[i] = luaString;
    }
    return concatLuaString(luaStrings);
  }

  public static LuaString concat(LuaValue a, LuaValue b) {
    return concat(new LuaValue[] {a, b});
  }
}
