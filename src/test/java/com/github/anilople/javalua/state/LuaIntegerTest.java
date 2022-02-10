package com.github.anilople.javalua.state;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class LuaIntegerTest {

  @Test
  void fromLuaNumberCase1() {
    var r = LuaInteger.fromLuaNumber(LuaNumber.ZERO);
    assertTrue(r.r1);
    assertEquals(LuaInteger.ZERO, r.r0);
  }

  @Test
  void fromLuaNumberCase2() {
    var r = LuaInteger.fromLuaNumber(LuaValue.of(7.0D));
    assertTrue(r.r1);
    assertEquals(LuaValue.of(7), r.r0);
  }

  @Test
  void fromLuaNumberCase3() {
    var r = LuaInteger.fromLuaNumber(LuaValue.of(7.5D));
    assertFalse(r.r1);
  }
}