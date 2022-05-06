package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaValue;
import com.github.anilople.javalua.util.Return3;

class ToLuaIntegerConverter {

  private static final Return3<Boolean, LuaInteger, LuaInteger> ERROR_RETURN =
      new Return3<>(false, null, null);

  static Return3<Boolean, LuaInteger, LuaInteger> convert(LuaValue a, LuaValue b) {
    if (!a.canConvertToLuaInteger()) {
      return ERROR_RETURN;
    }
    if (!b.canConvertToLuaInteger()) {
      return ERROR_RETURN;
    }
    return new Return3<>(true, a.toLuaInteger(), b.toLuaInteger());
  }
}
