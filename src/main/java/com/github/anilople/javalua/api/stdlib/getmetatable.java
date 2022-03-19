package com.github.anilople.javalua.api.stdlib;

import com.github.anilople.javalua.state.LuaState;

/**
 * page 219
 *
 * @author wxq
 */
public class getmetatable extends AbstractJavaFunction {

  @Override
  public Integer apply(LuaState luaState) {
    boolean success = luaState.getMetaTable(1);
    if (!success) {
      luaState.pushLuaNil();
    }
    return 1;
  }
}
