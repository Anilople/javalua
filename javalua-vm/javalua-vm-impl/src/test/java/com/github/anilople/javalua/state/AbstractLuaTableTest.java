package com.github.anilople.javalua.state;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class AbstractLuaTableTest {

  @Test
  @DisplayName("默认构造没有 meta table")
  void existsMetaTable_case1() {
    assertFalse(new ForTestAbstractLuaTable().existsMetaTable());
  }

  @Test
  @DisplayName("存在 meta table")
  void existsMetaTable_case2() {
    AbstractLuaTable luaTable = new ForTestAbstractLuaTable();
    AbstractLuaTable metaTable = new ForTestAbstractLuaTable();
    luaTable.setMetaTable(metaTable);
    assertTrue(luaTable.existsMetaTable());
    assertEquals(metaTable, luaTable.getMetaTable());

    // clear
    luaTable.removeMetaTable();
    assertFalse(luaTable.existsMetaTable());
    assertThrows(RuntimeException.class, luaTable::getMetaTable);
  }

  /**
   * Only use by test
   *
   * @author wxq
   */
  private static class ForTestAbstractLuaTable extends AbstractLuaTable {

    @Override
    public void init(int arraySize, int mapSize) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(LuaValue key) {
      throw new UnsupportedOperationException();
    }

    @Override
    public LuaValue get(LuaValue key) {
      throw new UnsupportedOperationException();
    }

    @Override
    public LuaTable put(LuaValue key, LuaValue value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public LuaValue remove(LuaValue key) {
      throw new UnsupportedOperationException();
    }

    @Override
    public LuaInteger length() {
      throw new UnsupportedOperationException();
    }

    @Override
    public LuaValue nextKey(LuaValue currentKey) {
      throw new UnsupportedOperationException();
    }
  }
}
