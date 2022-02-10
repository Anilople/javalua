package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.exception.TypeConversionRuntimeException;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;

class ToLuaStringConverter {

  static LuaString convert(LuaValue luaValue) {
    var r = LuaString.from(luaValue);
    if (!r.r1) {
      throw new TypeConversionRuntimeException(luaValue, LuaString.class);
    }
    return r.r0;
  }

}
