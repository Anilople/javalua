package com.github.anilople.javalua.constant;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaValue;

/**
 * @author wxq
 * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h">lua.h</a>
 */
public interface LuaConstants {

  /**
   * 栈的最小大小
   */
  int LUA_MIN_STACK = 20;

  int LUA_I_MAX_STACK = 1000000;
  /**
   * 注册表的索引。
   * 伪索引 Pseudo-indices
   * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h#L42">LUA_REGISTRYINDEX	(-LUAI_MAXSTACK - 1000)</a>
   */
  int LUA_REGISTRY_INDEX = -LUA_I_MAX_STACK - 1000;

  /**
   * 全局环境在注册表里的索引
   */
  LuaInteger LUA_RIDX_GLOBALS = LuaValue.of(2L);
}
