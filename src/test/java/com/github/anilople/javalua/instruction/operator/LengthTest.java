package com.github.anilople.javalua.instruction.operator;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.state.LuaValue;
import org.junit.jupiter.api.Test;

class LengthTest {

  @Test
  void length() {
    assertEquals(LuaValue.of(0), Length.length(LuaValue.of("")));
    assertEquals(LuaValue.of(1), Length.length(LuaValue.of("1")));
    assertEquals(LuaValue.of(2), Length.length(LuaValue.of("12")));
    assertEquals(LuaValue.of(5), Length.length(LuaValue.of("hello")));
  }
}