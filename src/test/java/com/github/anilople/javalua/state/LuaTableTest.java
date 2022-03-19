package com.github.anilople.javalua.state;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}