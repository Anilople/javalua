package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaValue;
import com.github.anilople.javalua.util.Return3;

class ToLuaIntegerConverter {

  static LuaInteger convert(LuaValue a) {
    var r = LuaInteger.from(a);
    return r.r0;
  }

  private static final Return3<Boolean, LuaInteger, LuaInteger> ERROR_RETURN =
      new Return3<>(false, null, null);

  /**
   * @return true 如果可以转成{@link LuaInteger}
   */
  static boolean canConvert(LuaValue a) {
    // TODO, 性能
    return LuaInteger.from(a).r1;
  }

  static Return3<Boolean, LuaInteger, LuaInteger> convert(LuaValue a, LuaValue b) {
    var aR = LuaInteger.from(a);
    var bR = LuaInteger.from(b);

    if (aR.r1 && bR.r1) {
      return new Return3<>(true, aR.r0, bR.r0);
    } else {
      return ERROR_RETURN;
    }
  }

}
