package com.github.anilople.javalua.exception;

import com.github.anilople.javalua.api.stdlib.error;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 当调用{@link error}时，在java层面抛出的异常
 *
 * @author wxq
 */
public class LuaErrorRuntimeException extends RuntimeException {

  private final LuaValue luaValue;

  public LuaErrorRuntimeException(LuaValue luaValue) {
    this.luaValue = luaValue;
  }

  public LuaValue getLuaValue() {
    return luaValue;
  }
}
