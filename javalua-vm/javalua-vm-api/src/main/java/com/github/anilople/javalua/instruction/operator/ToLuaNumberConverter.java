package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaNumber;
import com.github.anilople.javalua.state.LuaValue;
import com.github.anilople.javalua.util.Return3;

class ToLuaNumberConverter {

  private static final Return3<Boolean, LuaNumber, LuaNumber> ERROR_RETURN =
      new Return3<>(false, null, null);

  static Return3<Boolean, LuaNumber, LuaNumber> convert(LuaValue a, LuaValue b) {
    if (!a.canConvertToLuaNumber()) {
      return ERROR_RETURN;
    }
    if (!b.canConvertToLuaNumber()) {
      return ERROR_RETURN;
    }
    return new Return3<>(true, a.toLuaNumber(), b.toLuaNumber());
  }
}
