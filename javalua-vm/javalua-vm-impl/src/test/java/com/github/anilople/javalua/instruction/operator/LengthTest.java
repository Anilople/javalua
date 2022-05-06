package com.github.anilople.javalua.instruction.operator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.state.*;
import org.junit.jupiter.api.Test;

class LengthTest {

  @Test
  void length() {
    assertEquals(LuaInteger.newLuaInteger(0), Length.length(LuaString.newLuaString("")));
    assertEquals(LuaInteger.newLuaInteger(1), Length.length(LuaString.newLuaString("1")));
    assertEquals(LuaInteger.newLuaInteger(2), Length.length(LuaString.newLuaString("12")));
    assertEquals(LuaInteger.newLuaInteger(5), Length.length(LuaString.newLuaString("hello")));
  }
}
