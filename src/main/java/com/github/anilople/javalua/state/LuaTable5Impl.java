package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import java.util.HashMap;
import java.util.Map;

/**
 * lua 5.0 的table实现，为了性能引入数组{@link #array}
 *
 * @author wxq
 */
class LuaTable5Impl extends AbstractLuaTable {

  static LuaTable5Impl ofArray(int arraySize) {
    LuaTable5Impl luaTable = new LuaTable5Impl();
    luaTable.array = new LuaValue[arraySize];
    return luaTable;
  }

  static LuaTable5Impl ofMap(int mapSize) {
    LuaTable5Impl luaTable = new LuaTable5Impl();
    luaTable.map = new HashMap<>(mapSize);
    return luaTable;
  }

  /**
   * lua 5.0 开始引入array来提高table的性能
   */
  LuaValue[] array;

  Map<LuaValue, LuaValue> map;

  @Override
  public LuaType type() {
    return LuaType.LUA_TTABLE;
  }

  private static final Return2<Integer, Boolean> CANNOT_RESOLVE_INDEX = new Return2<>(-1, false);

  /**
   * @return 数组下标,true 如果key可以作为数组的索引
   */
  static Return2<Integer, Boolean> resolveIndexOfKey(LuaValue key) {
    if (key instanceof LuaInteger) {
      var index = (int) ((LuaInteger) key).getValue();
      return new Return2<>(index, true);
    }
    if (key instanceof LuaNumber) {
      var r = LuaInteger.fromLuaNumber((LuaNumber) key);
      if (r.r1) {
        var index = (int) r.r0.getValue();
        return new Return2<>(index, true);
      }
    }
    return CANNOT_RESOLVE_INDEX;
  }

  @Override
  public boolean containsKey(LuaValue key) {
    throw new UnsupportedOperationException();
  }

  /**
   * 如果key是整数（或者是可以转为整数的浮点数），并且在数组索引范围之内，直接按索引访问数组部分
   */
  public LuaValue get(LuaValue key) {
    var indexOfKey = resolveIndexOfKey(key);
    if (indexOfKey.r1) {
      var index = indexOfKey.r0;
      return this.array[index - 1];
    } else {
      var value = this.map.get(key);
      return value != null ? value : LuaValue.NIL;
    }
  }

  /**
   * 把{@link #array}尾部的洞全部删除
   */
  void shrinkArray() {}

  public void put(LuaValue key, LuaValue value) {
    // check
    this.ensureKeyValid(key);

    // key
    var indexOfKey = resolveIndexOfKey(key);
    if (indexOfKey.r1) {
      var index = indexOfKey.r0;
      this.array[index - 1] = value;
    } else {
      this.map.put(key, value);
    }
  }

  @Override
  public LuaInteger length() {
    throw new UnsupportedOperationException();
  }
}
