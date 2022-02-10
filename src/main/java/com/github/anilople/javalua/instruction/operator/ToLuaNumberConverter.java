package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaNumber;
import com.github.anilople.javalua.state.LuaValue;
import com.github.anilople.javalua.util.Return3;

class ToLuaNumberConverter {

  private static final Return3<Boolean, LuaNumber, LuaNumber> ERROR_RETURN =
      new Return3<>(false, null, null);

  static Return3<Boolean, LuaNumber, LuaNumber> convert(LuaValue a, LuaValue b) {
    var aR = LuaNumber.from(a);
    var bR = LuaNumber.from(b);

    if (aR.r1 && bR.r1) {
      return new Return3<>(true, aR.r0, bR.r0);
    } else {
      return ERROR_RETURN;
    }
  }
}
