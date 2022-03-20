package com.github.anilople.javalua.api.stdlib;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.JavaFunction;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;

/**
 * @author wxq
 */
abstract class AbstractJavaFunction implements JavaFunction {

  @Override
  public void registerTo(LuaVM luaVM) {
    LuaString functionName = LuaValue.of(this.getClass().getSimpleName());
    luaVM.register(functionName, this);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
