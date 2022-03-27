package com.github.anilople.javalua.state;

/**
 * @author wxq
 */
abstract class AbstractLuaTable implements LuaTable {

  /**
   * 元表 page 207
   */
  private LuaValue metaTable = LuaValue.NIL;

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

  @Override
  public boolean existsMetaTable() {
    return !LuaValue.NIL.equals(this.metaTable);
  }

  @Override
  public LuaTable getMetaTable() {
    return (LuaTable) metaTable;
  }

  @Override
  public void removeMetaTable() {
    this.metaTable = LuaValue.NIL;
  }

  @Override
  public void setMetaTable(LuaTable metatable) {
    this.metaTable = metatable;
  }
}
