package com.github.anilople.javalua.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * lua 5.0 之前的table实现，没有为了性能引入数组
 *
 * @author wxq
 */
public class LuaTableBefore5Impl extends AbstractLuaTable {
  private final Map<LuaValue, LuaValue> map;
  private final int mapSize;

  /**
   * 遍历结束后会设置成 null
   *
   * 如果是null，表示遍历未开始
   */
  private Map<LuaValue, LuaValue> currentKey2NextKey;

  public LuaTableBefore5Impl() {
    throw new UnsupportedOperationException();
  }

  public LuaTableBefore5Impl(int arraySize, int mapSize) {
    int size = Math.max(arraySize, mapSize);
    this.map = new HashMap<>(size * 2);
    this.mapSize = mapSize;
  }

  static Map<LuaValue, LuaValue> generateCurrentKey2NextKey(Map<LuaValue, LuaValue> map) {
    Set<LuaValue> keys = map.keySet();
    Map<LuaValue, LuaValue> currentKey2NextKey = new HashMap<>(keys.size() * 2);

    LuaValue currentKey = LuaValue.NIL;
    for (LuaValue nextKey : keys) {
      currentKey2NextKey.put(currentKey, nextKey);
      currentKey = nextKey;
    }
    currentKey2NextKey.put(currentKey, LuaValue.NIL);
    return currentKey2NextKey;
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
  public LuaTable put(LuaValue key, LuaValue value) {
    this.ensureKeyValid(key);
    this.map.put(key, value);
    if (this.map.size() > this.mapSize) {
      //      throw new IllegalStateException("table's size overload, maximum is " + this.mapSize);
    }
    return this;
  }

  @Override
  public LuaValue remove(LuaValue key) {
    var value = this.map.remove(key);
    return value == null ? LuaValue.NIL : value;
  }

  @Override
  public LuaInteger length() {
    var size = 0;
    for (int i = 1; this.map.containsKey(LuaInteger.newLuaInteger(i)); i++) {
      size++;
    }
    return LuaInteger.newLuaInteger(size);
  }

  @Override
  public LuaValue nextKey(LuaValue currentKey) {
    if (null == currentKey) {
      throw new IllegalArgumentException("cannot be null should use nil i.e " + LuaValue.NIL);
    }
    if (currentKey.isLuaNil()) {
      // 遍历未开始
      if (null == this.currentKey2NextKey) {
        this.currentKey2NextKey = generateCurrentKey2NextKey(this.map);
      } else {
        throw new IllegalStateException("key iterator exists already");
      }
    } else {
      // 使用者期望已经在遍历中
      if (null == this.currentKey2NextKey) {
        // 实际上遍历已经结束
        throw new IllegalStateException("key iterator finished already");
      } else {

      }
    }

    LuaValue nextKey = this.currentKey2NextKey.get(currentKey);
    if (nextKey.isLuaNil()) {
      // 遍历结束
      this.currentKey2NextKey = null;
    }
    return nextKey;
  }

  @Override
  public String toString() {
    List<String> pairs = new ArrayList<>();
    for (Map.Entry<LuaValue, LuaValue> entry : this.map.entrySet()) {
      var key = entry.getKey();
      var value = entry.getValue();
      StringBuilder stringBuilder = new StringBuilder();
      if (key instanceof LuaTable) {
        stringBuilder.append("table: ").append(Long.toHexString(key.hashCode()));
      } else {
        stringBuilder.append(key.toString());
      }
      stringBuilder.append(" = ");
      if (value instanceof LuaTable) {
        stringBuilder.append("table: ").append(Long.toHexString(value.hashCode()));
      } else {
        stringBuilder.append(value.toString());
      }
      pairs.add(stringBuilder.toString());
    }
    return "Table:" + this.mapSize + " {" + String.join(",", pairs) + "}";
  }

  @Override
  public LuaString toLuaString() {
    throw new UnsupportedOperationException();
  }
}
