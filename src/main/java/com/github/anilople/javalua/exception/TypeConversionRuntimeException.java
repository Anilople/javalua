package com.github.anilople.javalua.exception;

import com.github.anilople.javalua.state.LuaValue;

public class TypeConversionRuntimeException extends RuntimeException {
  public TypeConversionRuntimeException(LuaValue luaValue, Class<? extends LuaValue> targetType) {
    super("fail to convert " + luaValue + " to " + targetType);
  }
}
