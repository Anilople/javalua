package com.github.anilople.javalua.state;

import com.github.anilople.javalua.exception.TypeConversionRuntimeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author wxq
 */
class LuaIntegerTest {

  @Test
  void fromLuaNumberCase1() {
    var r = LuaNumber.ZERO.toLuaInteger();
    assertTrue(LuaNumber.ZERO.canConvertToLuaInteger());
    assertEquals(LuaInteger.ZERO, LuaNumber.ZERO.toLuaInteger());
  }

  @Test
  void fromLuaNumberCase2() {
    LuaNumber luaNumber = LuaNumber.newLuaNumber(7.0D);
    assertTrue(luaNumber.canConvertToLuaInteger());
    assertEquals(LuaInteger.newLuaInteger(7), luaNumber.toLuaInteger());
  }

  @Test
  void fromLuaNumberCase3() {
    LuaNumber luaNumber = LuaNumber.newLuaNumber(7.5D);
    assertFalse(luaNumber.canConvertToLuaInteger());
    assertThrows(TypeConversionRuntimeException.class, luaNumber::toLuaInteger);
  }
}
