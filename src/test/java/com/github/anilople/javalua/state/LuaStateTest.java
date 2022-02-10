package com.github.anilople.javalua.state;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class LuaStateTest {

  @Test
  void testSetTopCase1() {
    LuaState luaState = LuaState.create();
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
    LuaState luaState = LuaState.create();
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
    LuaState luaState = LuaState.create();
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

    LuaState luaState = LuaState.create();
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

  /**
   * page 87
   */
  @Test
  void operatorBookCase() {
    LuaState luaState = LuaState.create();
    luaState.pushLuaInteger(LuaValue.of(1));
    luaState.pushLuaString(LuaValue.of("2.0"));
    luaState.pushLuaString(LuaValue.of("3.0"));
    luaState.pushLuaNumber(LuaValue.of(4.0));
    // [1.0]["2.0"]["3.0"][4]
    LuaState.printStack(luaState);

    luaState.arithmetic(ArithmeticOperator.LUA_OPADD);
    // [1.0]["2.0"][7]
    LuaState.printStack(luaState);
    assertTrue(luaState.isLuaInteger(3));

    luaState.bitwise(BitwiseOperator.LUA_OPBNOT);
    // [1]["2.0"][-8]
    LuaState.printStack(luaState);
    assertEquals(LuaValue.of(-8), luaState.toLuaInteger(3));

    luaState.len(2);
    // [1]["2.0"][-8][3]
    LuaState.printStack(luaState);
    assertEquals(LuaValue.of(3), luaState.toLuaInteger(4));

    luaState.concat(3);
    // [1]["2.0-83"]
    LuaState.printStack(luaState);
    assertEquals(LuaValue.of("2.0-83"), luaState.toLuaString(2));

    var luaBoolean = luaState.compare(1, 2, ComparisonOperator.LUA_OPEQ);
    luaState.pushLuaBoolean(luaBoolean);
    // [1]["2.0-83"][false]
    LuaState.printStack(luaState);
    assertEquals(LuaBoolean.FALSE, luaState.toLuaBoolean(3));
  }
}