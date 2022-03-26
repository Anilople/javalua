package com.github.anilople.javalua.api.stdlib;

import com.github.anilople.javalua.state.LuaState;

/**
 * page 220
 *
 * @author wxq
 */
public class setmetatable extends AbstractJavaFunction {

  private static final setmetatable INSTANCE = new setmetatable();

  public static setmetatable getInstance() {
    return INSTANCE;
  }

  private setmetatable() {}

  @Override
  public Integer apply(LuaState luaState) {
    luaState.setMetaTable(1);
    return 1;
  }
}
