package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringConcatTest {

  @Test
  void concatCase1() {
    assertEquals(
        LuaString.newLuaString("abc"),
        StringConcat.concat(LuaString.newLuaString("a"), LuaString.newLuaString("b"), LuaString.newLuaString("c")));
  }

  @Test
  void concatCase2() {
    assertEquals(
        LuaString.newLuaString("123"), StringConcat.concat(LuaInteger.newLuaInteger(1), LuaInteger.newLuaInteger(2), LuaInteger.newLuaInteger(3)));
  }

  @Test
  void canConcat() {
    assertTrue(StringConcat.canConcat(LuaInteger.newLuaInteger(-8)));
    assertTrue(StringConcat.canConcat(LuaInteger.newLuaInteger(-8), LuaInteger.newLuaInteger(3)));
  }
}
