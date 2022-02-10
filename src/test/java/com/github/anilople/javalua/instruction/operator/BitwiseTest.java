package com.github.anilople.javalua.instruction.operator;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.state.LuaValue;
import org.junit.jupiter.api.Test;

class BitwiseTest {

  @Test
  void and() {
    assertEquals(LuaValue.of(0), Bitwise.and(LuaValue.of(1), LuaValue.of(2)));
  }

  @Test
  void or() {
    assertEquals(LuaValue.of(3), Bitwise.or(LuaValue.of(1), LuaValue.of(2)));
  }

  @Test
  void xor() {
    assertEquals(LuaValue.of(3), Bitwise.xor(LuaValue.of(1), LuaValue.of(2)));
  }

  @Test
  void negate() {
    assertEquals(LuaValue.of(0), Bitwise.negate(LuaValue.of(-1)));
  }

  @Test
  void shiftRight() {
    assertEquals(LuaValue.of(1), Bitwise.shiftRight(LuaValue.of(3), LuaValue.of(1)));
    assertEquals(LuaValue.of(0), Bitwise.shiftRight(LuaValue.of(3), LuaValue.of(2)));

    assertEquals(LuaValue.of(0), Bitwise.shiftRight(LuaValue.of(Long.MIN_VALUE), LuaValue.of(-1)));
    assertEquals(
        LuaValue.of(-(Long.MIN_VALUE / 2)),
        Bitwise.shiftRight(LuaValue.of(Long.MIN_VALUE), LuaValue.of(1)));

    assertEquals(LuaValue.of(1), Bitwise.shiftRight(LuaValue.of(-1L), LuaValue.of(63)));
  }

  @Test
  void shiftLeft() {
    assertEquals(LuaValue.of(2), Bitwise.shiftLeft(LuaValue.of(1), LuaValue.of(1)));
    assertEquals(LuaValue.of(6), Bitwise.shiftLeft(LuaValue.of(3), LuaValue.of(1)));

    assertEquals(
        LuaValue.of(-(Long.MIN_VALUE / 2)),
        Bitwise.shiftLeft(LuaValue.of(Long.MIN_VALUE), LuaValue.of(-1)));
    assertEquals(LuaValue.of(0), Bitwise.shiftLeft(LuaValue.of(Long.MIN_VALUE), LuaValue.of(1)));

    assertEquals(LuaValue.of(-2), Bitwise.shiftLeft(LuaValue.of(Long.MAX_VALUE), LuaValue.of(1)));

    assertEquals(LuaValue.of(1), Bitwise.shiftLeft(LuaValue.of(2), LuaValue.of(-1)));
    assertEquals(LuaValue.of(2), Bitwise.shiftLeft(LuaValue.of("1"), LuaValue.of(1.0D)));
  }
}
