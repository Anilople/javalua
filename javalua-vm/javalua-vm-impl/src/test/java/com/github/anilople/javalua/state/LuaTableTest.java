package com.github.anilople.javalua.state;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class LuaTableTest {

  @Test
  @DisplayName("toString不会stack over flow")
  void testToStringStackOverFlow() {
    var t1 = LuaTable.of(10, 10);
    var t2 = LuaTable.of(10, 10);
    t1.put(LuaValue.of("t2"), t2);
    t2.put(LuaValue.of("t1"), t1);
    t1.toString();
    t2.toString();
  }

  @Test
  void testNextKeyNull() {
    var table = LuaTable.of(10, 10);
    assertThrows(IllegalArgumentException.class, () -> table.nextKey(null));
  }

  @Test
  void testNextKeySize0() {
    var table = LuaTable.of(10, 10);
    assertTrue(table.nextKey(LuaValue.NIL).isLuaNil());
  }

  @Test
  void testNextKeySize1() {
    var table = LuaTable.of(10, 10);
    LuaValue key1 = LuaValue.of("key1");
    LuaValue value1 = LuaValue.of("value1");
    table.put(key1, value1);

    assertEquals(key1, table.nextKey(LuaValue.NIL));
    assertTrue(table.nextKey(key1).isLuaNil());

    assertThrows(IllegalStateException.class, () -> table.nextKey(key1));
  }

  @Test
  void testNextKeySize100() {
    var table = LuaTable.of(10, 10);
    Set<LuaValue> keysExpected = new HashSet<>();
    for (int i = 0; i < 100; i++) {
      LuaValue key = LuaValue.of("key" + i);
      LuaValue value = LuaValue.of("value" + i);
      table.put(key, value);
      keysExpected.add(key);
    }

    Set<LuaValue> keysActual = new HashSet<>();
    for (LuaValue key = table.nextKey(LuaValue.NIL);
        !key.isLuaNil();
        key = table.nextKey(key)) {
      keysActual.add(key);
    }
    assertArrayEquals(keysExpected.toArray(), keysActual.toArray());
  }
}
