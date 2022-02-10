package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.exception.TypeConversionRuntimeException;
import com.github.anilople.javalua.state.LuaNumber;
import com.github.anilople.javalua.state.LuaValue;
import com.github.anilople.javalua.util.Return2;

class ToLuaNumberConverter {

  /**
   * @return 2个 {@link LuaNumber}
   * @throws TypeConversionRuntimeException 如果转换失败
   */
  static Return2<LuaNumber, LuaNumber> convert(LuaValue a, LuaValue b) {
    final LuaNumber aLuaNumber;
    if (a instanceof LuaNumber) {
      aLuaNumber = (LuaNumber) a;
    } else {
      var aReturn2 = LuaNumber.from(a);
      if (!aReturn2.r1) {
        throw new TypeConversionRuntimeException(a, LuaNumber.class);
      } else {
        aLuaNumber = aReturn2.r0;
      }
    }

    final LuaNumber bLuaNumber;
    if (b instanceof LuaNumber) {
      bLuaNumber = (LuaNumber) b;
    } else {
      var bReturn2 = LuaNumber.from(b);
      if (!bReturn2.r1) {
        throw new TypeConversionRuntimeException(b, LuaNumber.class);
      } else {
        bLuaNumber = bReturn2.r0;
      }
    }

    return new Return2<>(aLuaNumber, bLuaNumber);
  }
}
