package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitwiseTest {

  @Test
  void and() {
    assertEquals(LuaInteger.newLuaInteger(0), Bitwise.and(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(2)));
  }

  @Test
  void or() {
    assertEquals(LuaInteger.newLuaInteger(3), Bitwise.or(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(2)));
  }

  @Test
  void xor() {
    assertEquals(LuaInteger.newLuaInteger(3), Bitwise.xor(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(2)));
  }

  @Test
  void negate() {
    assertEquals(LuaInteger.newLuaInteger(0), Bitwise.negate(LuaInteger.newLuaInteger(-1)));
  }

  @Test
  void shiftRight() {
    assertEquals(LuaInteger.newLuaInteger(1), Bitwise.shiftRight(LuaInteger.newLuaInteger(3), LuaInteger.newLuaInteger(1)));
    assertEquals(LuaInteger.newLuaInteger(0), Bitwise.shiftRight(LuaInteger.newLuaInteger(3), LuaInteger.newLuaInteger(2)));

    assertEquals(LuaInteger.newLuaInteger(0), Bitwise.shiftRight(LuaInteger.newLuaInteger(Long.MIN_VALUE), LuaInteger.newLuaInteger(-1)));
    assertEquals(
        LuaInteger.newLuaInteger(-(Long.MIN_VALUE / 2)),
        Bitwise.shiftRight(LuaInteger.newLuaInteger(Long.MIN_VALUE), LuaInteger.newLuaInteger(1)));

    assertEquals(LuaInteger.newLuaInteger(1), Bitwise.shiftRight(LuaInteger.newLuaInteger(-1L), LuaInteger.newLuaInteger(63)));
  }

  @Test
  void shiftLeft() {
    assertEquals(LuaInteger.newLuaInteger(2), Bitwise.shiftLeft(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(1)));
    assertEquals(LuaInteger.newLuaInteger(6), Bitwise.shiftLeft(LuaInteger.newLuaInteger(3), LuaInteger.newLuaInteger(1)));

    assertEquals(
        LuaInteger.newLuaInteger(-(Long.MIN_VALUE / 2)),
        Bitwise.shiftLeft(LuaInteger.newLuaInteger(Long.MIN_VALUE), LuaInteger.newLuaInteger(-1)));
    assertEquals(LuaInteger.newLuaInteger(0), Bitwise.shiftLeft(LuaInteger.newLuaInteger(Long.MIN_VALUE), LuaInteger.newLuaInteger(1)));

    assertEquals(LuaInteger.newLuaInteger(-2), Bitwise.shiftLeft(LuaInteger.newLuaInteger(Long.MAX_VALUE), LuaInteger.newLuaInteger(1)));

    assertEquals(LuaInteger.newLuaInteger(1), Bitwise.shiftLeft(LuaInteger.newLuaInteger(2), LuaInteger.newLuaInteger(-1)));
    assertEquals(LuaInteger.newLuaInteger(2), Bitwise.shiftLeft(LuaString.newLuaString("1"), LuaNumber.newLuaNumber(1.0D)));
  }
}
