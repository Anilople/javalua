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
   * key在表中是否存在
   *
   * @return true 如果key在表中存在
   */
  boolean containsKey(LuaValue key);

  /**
   * @return {@link LuaValue#NIL}如果key不存在
   */
  LuaValue get(LuaValue key);

  void put(LuaValue key, LuaValue value);

  LuaValue remove(LuaValue key);

  /**
   * page 124
   * @return 数组的长度
   */
  LuaInteger length();

  void setMetaTable(LuaTable luaTable);

  void removeMetaTable();

  boolean existsMetaTable();

  LuaTable getMetaTable();

  /**
   * page 227
   *
   * 遍历table时，根据当前的key获取下一个key
   *
   * @return {@link LuaValue#NIL}如果所有的key都遍历完成
   */
  LuaValue nextKey(LuaValue currentKey);

  @Override
  default LuaType type() {
    return LuaType.LUA_TTABLE;
  }
}
