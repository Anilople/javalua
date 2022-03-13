package com.github.anilople.javalua.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * lua 5.0 之前的table实现，没有为了性能引入数组
 *
 * @author wxq
 */
class LuaTableBefore5Impl extends AbstractLuaTable {
  private Map<LuaValue, LuaValue> map;
  private final int mapSize;

  LuaTableBefore5Impl(int mapSize) {
    this.map = new HashMap<>(mapSize * 2);
    this.mapSize = mapSize;
  }

  @Override
  public boolean containsKey(LuaValue key) {
    return this.map.containsKey(key);
  }

  @Override
  public LuaValue get(LuaValue key) {
    var value = this.map.get(key);
    return null != value ? value : LuaValue.NIL;
  }

  @Override
  public void put(LuaValue key, LuaValue value) {
    this.ensureKeyValid(key);
    this.map.put(key, value);
    if (this.map.size() > this.mapSize) {
      //      throw new IllegalStateException("table's size overload, maximum is " + this.mapSize);
    }
  }

  @Override
  public LuaInteger length() {
    var size = 0;
    for (int i = 1; this.map.containsKey(LuaValue.of(i)); i++) {
      size++;
    }
    return LuaValue.of(size);
  }

  @Override
  public String toString() {
    List<String> pairs = new ArrayList<>();
    for (Map.Entry<LuaValue, LuaValue> entry : this.map.entrySet()) {
      String pair = entry.getKey() + " = " + entry.getValue();
      pairs.add(pair);
    }
    return "Table:" + this.mapSize + " {" + String.join(",", pairs) + "}";
  }
}
