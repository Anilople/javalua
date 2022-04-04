package com.github.anilople.javalua.api.stdlib;

import com.github.anilople.javalua.constant.LuaConstants.ThreadStatus;
import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaValue;

/**
 * page 239
 *
 * pcall和call不同，pcall会捕获函数调用过程中产生的错误，call会把错误传播出去
 *
 * @author wxq
 */
public class pcall extends AbstractJavaFunction {

  private static final pcall INSTANCE = new pcall();

  public static pcall getInstance() {
    return INSTANCE;
  }

  private pcall() {}

  @Override
  public Integer apply(LuaState luaState) {
    var nArgs = luaState.getTop() - 1;
    var status = luaState.pcall(nArgs, -1, 0);
    if (ThreadStatus.LUA_OK.equals(status)) {
      luaState.pushLuaBoolean(LuaValue.TRUE);
    } else {
      luaState.pushLuaBoolean(LuaValue.FALSE);
    }
    luaState.insert(1);
    return luaState.getTop();
  }
}