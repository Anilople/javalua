package com.github.anilople.javalua.instruction.operator;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.state.LuaValue;
import org.junit.jupiter.api.Test;

class StringConcatTest {

  @Test
  void concatCase1() {
    assertEquals(
        LuaValue.of("abc"),
        StringConcat.concat(LuaValue.of("a"), LuaValue.of("b"), LuaValue.of("c")));
  }

  @Test
  void concatCase2() {
    assertEquals(
        LuaValue.of("123"), StringConcat.concat(LuaValue.of(1), LuaValue.of(2), LuaValue.of(3)));
  }

  @Test
  void canConcat() {
    assertTrue(StringConcat.canConcat(LuaValue.of(-8)));
    assertTrue(StringConcat.canConcat(LuaValue.of(-8), LuaValue.of(3)));
  }
}
