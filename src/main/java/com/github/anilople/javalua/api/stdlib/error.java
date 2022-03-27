package com.github.anilople.javalua.api.stdlib;

import com.github.anilople.javalua.exception.LuaErrorRuntimeException;
import com.github.anilople.javalua.state.LuaState;

/**
 * page 238
 *
 * @author wxq
 */
public class error extends AbstractJavaFunction {

  private static final error INSTANCE = new error();

  public static error getInstance() {
    return INSTANCE;
  }

  private error() {}

  @Override
  public Integer apply(LuaState luaState) {
    return luaState.error();
  }
}
