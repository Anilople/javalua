package com.github.anilople.javalua.stdlib;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.*;
import com.github.anilople.javalua.state.JavaFunction;
import com.github.anilople.javalua.state.LuaString;

/**
 * @author wxq
 */
abstract class AbstractJavaFunction implements JavaFunction {

  @Override
  public void registerTo(LuaVM luaVM) {
    LuaString functionName = LuaString.newLuaString(this.getClass().getSimpleName());
    luaVM.register(functionName, this);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
