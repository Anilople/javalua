package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.constant.LuaConstants;

/**
 * 和Upvalue相关的指令一共有5条
 *
 * @author wxq
 */
abstract class UpvalueInstruction extends AbstractInstruction {
  public UpvalueInstruction(int originCodeValue) {
    super(originCodeValue);
  }

  /**
   * page 194
   *
   * @param index upvalue索引
   * @return upvalue伪索引
   */
  static int luaUpvalueIndex(int index) {
    return LuaConstants.LUA_REGISTRY_INDEX - index;
  }
}
