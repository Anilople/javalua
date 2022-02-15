package com.github.anilople.javalua.state;

/**
 * @author wxq
 */
abstract class AbstractLuaTable implements LuaTable {

  void ensureKeyValid(LuaValue key) {
    if (null == key) {
      throw new IllegalArgumentException("cannot be java null");
    }
    if (LuaValue.NIL.equals(key)) {
      throw new IllegalArgumentException("cannot be lua nil");
    }
    if (key instanceof LuaNumber) {
      LuaNumber luaNumber = (LuaNumber) key;
      if (luaNumber.isNaN()) {
        throw new IllegalArgumentException("NaN " + luaNumber);
      }
    }
  }
}
