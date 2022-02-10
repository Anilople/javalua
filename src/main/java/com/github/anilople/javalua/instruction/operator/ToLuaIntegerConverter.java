package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.exception.TypeConversionRuntimeException;
import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaValue;
import com.github.anilople.javalua.util.Return2;

class ToLuaIntegerConverter {

  /**
   * @return 2个 {@link LuaInteger}
   * @throws TypeConversionRuntimeException 如果转换失败
   */
  static Return2<LuaInteger, LuaInteger> convert(LuaValue a, LuaValue b) {
    final LuaInteger aLuaInteger;
    if (a instanceof LuaInteger) {
      aLuaInteger = (LuaInteger) a;
    } else {
      var aReturn2 = LuaInteger.from(a);
      if (!aReturn2.r1) {
        throw new TypeConversionRuntimeException(a, LuaInteger.class);
      } else {
        aLuaInteger = aReturn2.r0;
      }
    }

    final LuaInteger bLuaInteger;
    if (b instanceof LuaInteger) {
      bLuaInteger = (LuaInteger) b;
    } else {
      var bReturn2 = LuaInteger.from(b);
      if (!bReturn2.r1) {
        throw new TypeConversionRuntimeException(b, LuaInteger.class);
      } else {
        bLuaInteger = bReturn2.r0;
      }
    }

    return new Return2<>(aLuaInteger, bLuaInteger);
  }

  static LuaInteger convert(LuaValue a) {
    var r = LuaInteger.from(a);
    return r.r0;
  }
}
