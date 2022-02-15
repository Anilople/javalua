package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;

/**
 * @author wxq
 */
public interface LuaTable extends LuaValue {
  static LuaTable of(int arraySize, int mapSize) {
    // TODO, lua 5.0 开始支持数组
    return new LuaTableBefore5Impl(Math.max(arraySize, mapSize));
  }
  /**
   * @return {@link LuaValue#NIL}如果key不存在
   */
  LuaValue get(LuaValue key);
  void put(LuaValue key, LuaValue value);

  /**
   * page 124
   * @return 数组的长度
   */
  LuaInteger length();
  @Override
  default LuaType type() {
    return LuaType.LUA_TTABLE;
  }
}
