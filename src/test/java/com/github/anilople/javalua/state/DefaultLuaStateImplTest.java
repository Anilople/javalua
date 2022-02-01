package com.github.anilople.javalua.state;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DefaultLuaStateImplTest {

  @Test
  void testSetTopCase1() {
    DefaultLuaStateImpl luaState = new DefaultLuaStateImpl();
    assertEquals(0, luaState.getTop());

    luaState.setTop(1);
    assertEquals(1, luaState.getTop());

    luaState.setTop(15);
    assertEquals(15, luaState.getTop());
    assertTrue(luaState.isLuaNil(15));

    luaState.pop(1);
    assertEquals(14, luaState.getTop());
  }

  /**
   * page 69的案例
   */
  @Test
  void testBookCase() {
    DefaultLuaStateImpl luaState = new DefaultLuaStateImpl();
    luaState.pushLuaBoolean(LuaBoolean.TRUE);
    LuaState.printStack(luaState);
    luaState.pushLuaInteger(LuaValue.of(10));
    LuaState.printStack(luaState);
    luaState.pushLuaNil();
    LuaState.printStack(luaState);
    luaState.pushLuaString(LuaValue.of("hello"));
    LuaState.printStack(luaState);
    luaState.pushValue(-4);
    LuaState.printStack(luaState);

    // now it is [true][10.0][nil]["hello"][true]
    assertEquals(5, luaState.getTop());
    assertEquals(LuaBoolean.TRUE, luaState.toLuaBoolean(-1));

    luaState.replace(3);
    LuaState.printStack(luaState);
    assertEquals(4, luaState.getTop());

    luaState.setTop(6);
    LuaState.printStack(luaState);
    assertEquals(6, luaState.getTop());

    luaState.remove(-3);
    LuaState.printStack(luaState);
    luaState.setTop(-5);
    LuaState.printStack(luaState);
  }

  /**
   * page 64
   * <p/>
   * 图 4-12 Rotate() 示意图 1
   */
  @Test
  void rotateBookCase1() {
    DefaultLuaStateImpl luaState = new DefaultLuaStateImpl();
    luaState.pushLuaString(LuaValue.of("a"));
    luaState.pushLuaString(LuaValue.of("b"));
    luaState.pushLuaString(LuaValue.of("c"));
    luaState.pushLuaString(LuaValue.of("d"));
    luaState.pushLuaString(LuaValue.of("e"));

    luaState.rotate(2, 1);
    assertEquals(5, luaState.getTop());
    LuaState.printStack(luaState);
    assertEquals(LuaValue.of("a"), luaState.toLuaString(1));
    assertEquals(LuaValue.of("e"), luaState.toLuaString(2));
    assertEquals(LuaValue.of("b"), luaState.toLuaString(3));
    assertEquals(LuaValue.of("c"), luaState.toLuaString(4));
    assertEquals(LuaValue.of("d"), luaState.toLuaString(5));
  }

  /**
   * page 64
   * <p/>
   * 图 4-13 Rotate() 示意图 2
   */
  @Test
  void rotateBookCase2() {

    DefaultLuaStateImpl luaState = new DefaultLuaStateImpl();
    luaState.pushLuaString(LuaValue.of("a"));
    luaState.pushLuaString(LuaValue.of("b"));
    luaState.pushLuaString(LuaValue.of("c"));
    luaState.pushLuaString(LuaValue.of("d"));
    luaState.pushLuaString(LuaValue.of("e"));

    luaState.rotate(2, -1);
    assertEquals(5, luaState.getTop());
    LuaState.printStack(luaState);
    assertEquals(LuaValue.of("a"), luaState.toLuaString(1));
    assertEquals(LuaValue.of("c"), luaState.toLuaString(2));
    assertEquals(LuaValue.of("d"), luaState.toLuaString(3));
    assertEquals(LuaValue.of("e"), luaState.toLuaString(4));
    assertEquals(LuaValue.of("b"), luaState.toLuaString(5));
  }
}
