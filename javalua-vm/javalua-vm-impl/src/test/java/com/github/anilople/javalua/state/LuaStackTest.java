package com.github.anilople.javalua.state;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LuaStackTest {

  @Test
  void pushNil() {
    LuaStack luaStack = LuaStack.of(1);
    luaStack.push(LuaValue.NIL);
    assertThrows(IllegalStateException.class, () -> luaStack.push(LuaValue.NIL));
    assertTrue(luaStack.get(1).isLuaNil());
  }

  @Test
  void pushChaos() {
    LuaStack luaStack = LuaStack.of(10);
    luaStack.push(LuaValue.NIL);
    luaStack.push(LuaValue.TRUE);
    luaStack.push(LuaValue.FALSE);
    luaStack.push(LuaValue.of(1));
    luaStack.push(LuaValue.of(3D));
    assertEquals(5, luaStack.getTop());
  }

  @Test
  void popChaos() {
    LuaStack luaStack = LuaStack.of(10);
    luaStack.push(LuaValue.NIL);
    luaStack.push(LuaValue.TRUE);
    luaStack.push(LuaValue.FALSE);
    luaStack.push(LuaValue.of(1));
    luaStack.push(LuaValue.of(3D));
    assertEquals(5, luaStack.getTop());

    assertEquals(LuaValue.of(3D), luaStack.pop());
    assertEquals(LuaValue.of(1), luaStack.pop());
    assertTrue(luaStack.pop().isLuaFalse());
    assertTrue(luaStack.pop().isLuaTrue());
    assertTrue(luaStack.pop().isLuaNil());

    assertThrows(IllegalStateException.class, luaStack::pop);
  }

  @Test
  void reverseCase0() {
    LuaStack luaStack = LuaStack.of(10);
    luaStack.reverse(1, 1);
    assertThrows(RuntimeException.class, () -> luaStack.reverse(1, 2));
  }

  @Test
  void reverseCase1() {
    LuaStack luaStack = LuaStack.of(3);
    luaStack.push(LuaValue.NIL);
    luaStack.push(LuaValue.TRUE);
    luaStack.push(LuaValue.FALSE);
    luaStack.reverse(1, 3);
    assertTrue(luaStack.get(1).isLuaFalse());
    assertTrue(luaStack.get(2).isLuaTrue());
    assertTrue(luaStack.get(3).isLuaNil());
  }

  @Test
  void reverseCase2() {
    LuaStack luaStack = LuaStack.of(10);
    for (long value = 1; value <= 10; value++) {
      luaStack.push(LuaValue.of(value));
    }
    assertEquals(10, luaStack.getTop());

    luaStack.reverse(1, 10);

    for (long value = 1; value <= 10; value++) {
      assertEquals(LuaValue.of(value), luaStack.pop());
    }
  }
}
