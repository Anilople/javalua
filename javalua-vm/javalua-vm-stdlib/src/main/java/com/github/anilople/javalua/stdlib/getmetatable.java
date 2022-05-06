package com.github.anilople.javalua.stdlib;

import com.github.anilople.javalua.state.LuaState;

/**
 * page 219
 *
 * @author wxq
 */
public class getmetatable extends AbstractJavaFunction {

  private static final getmetatable INSTANCE = new getmetatable();

  public static getmetatable getInstance() {
    return INSTANCE;
  }

  public getmetatable() {}

  @Override
  public Integer apply(LuaState luaState) {
    boolean success = luaState.getMetaTable(1);
    if (!success) {
      luaState.pushLuaNil();
    }
    return 1;
  }
}
