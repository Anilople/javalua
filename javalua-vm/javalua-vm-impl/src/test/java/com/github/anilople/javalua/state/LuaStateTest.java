package com.github.anilople.javalua.state;

import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author wxq
 */
class LuaStateTest {

  @Test
  void testSetTopCase1() {
    LuaState luaState = LuaState.newLuaState();
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
    LuaState luaState = LuaState.newLuaState();
    luaState.pushLuaBoolean(LuaBoolean.TRUE);
    LuaState.printStack(luaState);
    luaState.pushLuaInteger(LuaInteger.newLuaInteger(10));
    LuaState.printStack(luaState);
    luaState.pushLuaNil();
    LuaState.printStack(luaState);
    luaState.pushLuaString(LuaString.newLuaString("hello"));
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
    LuaState luaState = LuaState.newLuaState();
    luaState.pushLuaString(LuaString.newLuaString("a"));
    luaState.pushLuaString(LuaString.newLuaString("b"));
    luaState.pushLuaString(LuaString.newLuaString("c"));
    luaState.pushLuaString(LuaString.newLuaString("d"));
    luaState.pushLuaString(LuaString.newLuaString("e"));

    luaState.rotate(2, 1);
    assertEquals(5, luaState.getTop());
    LuaState.printStack(luaState);
    assertEquals(LuaString.newLuaString("a"), luaState.toLuaString(1));
    assertEquals(LuaString.newLuaString("e"), luaState.toLuaString(2));
    assertEquals(LuaString.newLuaString("b"), luaState.toLuaString(3));
    assertEquals(LuaString.newLuaString("c"), luaState.toLuaString(4));
    assertEquals(LuaString.newLuaString("d"), luaState.toLuaString(5));
  }

  /**
   * page 64
   * <p/>
   * 图 4-13 Rotate() 示意图 2
   */
  @Test
  void rotateBookCase2() {

    LuaState luaState = LuaState.newLuaState();
    luaState.pushLuaString(LuaString.newLuaString("a"));
    luaState.pushLuaString(LuaString.newLuaString("b"));
    luaState.pushLuaString(LuaString.newLuaString("c"));
    luaState.pushLuaString(LuaString.newLuaString("d"));
    luaState.pushLuaString(LuaString.newLuaString("e"));

    luaState.rotate(2, -1);
    assertEquals(5, luaState.getTop());
    LuaState.printStack(luaState);
    assertEquals(LuaString.newLuaString("a"), luaState.toLuaString(1));
    assertEquals(LuaString.newLuaString("c"), luaState.toLuaString(2));
    assertEquals(LuaString.newLuaString("d"), luaState.toLuaString(3));
    assertEquals(LuaString.newLuaString("e"), luaState.toLuaString(4));
    assertEquals(LuaString.newLuaString("b"), luaState.toLuaString(5));
  }

  @Test
  @DisplayName("string和number相加 '3.0' + 4.0")
  void addStringAndNumber() {
    LuaState luaState = LuaState.newLuaState();
    luaState.pushLuaString(LuaString.newLuaString("3.0"));
    luaState.pushLuaNumber(LuaNumber.newLuaNumber(4.0));
    luaState.arithmetic(ArithmeticOperator.LUA_OPADD);
    LuaState.printStack(luaState);
    assertTrue(luaState.isLuaNumber(1));
    assertEquals(LuaNumber.newLuaNumber(7.0), luaState.toLuaNumber(1));
  }

  @Test
  @DisplayName("concat 2个lua number 整数")
  void concatLuaInteger() {
    LuaState luaState = LuaState.newLuaState();
    luaState.pushLuaInteger(LuaInteger.newLuaInteger(-8));
    luaState.pushLuaInteger(LuaInteger.newLuaInteger(3));
    luaState.concat(2);
    assertTrue(luaState.isLuaString(1));
    assertEquals(LuaString.newLuaString("-83"), luaState.toLuaString(1));
  }

  /**
   * page 87
   */
  @Test
  void operatorBookCase() {
    LuaState luaState = LuaState.newLuaState();
    luaState.pushLuaInteger(LuaInteger.newLuaInteger(1));
    luaState.pushLuaString(LuaString.newLuaString("2.0"));
    luaState.pushLuaString(LuaString.newLuaString("3.0"));
    luaState.pushLuaNumber(LuaNumber.newLuaNumber(4.0));
    // [1.0]["2.0"]["3.0"][4]
    LuaState.printStack(luaState);

    luaState.arithmetic(ArithmeticOperator.LUA_OPADD);
    // [1.0]["2.0"][7]
    LuaState.printStack(luaState);
    assertTrue(luaState.isLuaInteger(3));

    luaState.bitwise(BitwiseOperator.LUA_OPBNOT);
    // [1]["2.0"][-8]
    LuaState.printStack(luaState);
    assertEquals(LuaInteger.newLuaInteger(-8), luaState.toLuaInteger(3));

    luaState.len(2);
    // [1]["2.0"][-8][3]
    LuaState.printStack(luaState);
    assertEquals(LuaInteger.newLuaInteger(3), luaState.toLuaInteger(4));

    luaState.concat(3);
    // [1]["2.0-83"]
    LuaState.printStack(luaState);
    assertEquals(LuaString.newLuaString("2.0-83"), luaState.toLuaString(2));

    var luaBoolean = luaState.compare(1, 2, ComparisonOperator.LUA_OPEQ);
    luaState.pushLuaBoolean(luaBoolean);
    // [1]["2.0-83"][false]
    LuaState.printStack(luaState);
    assertEquals(LuaBoolean.FALSE, luaState.toLuaBoolean(3));
  }

  @Test
  void replaceEmpty() {
    LuaState luaState = LuaState.newLuaState();
    assertThrows(IllegalArgumentException.class, () -> luaState.replace(0));
    assertThrows(IllegalArgumentException.class, () -> luaState.replace(1));
  }

  @Test
  void replaceStackSize1() {
    LuaState luaState = LuaState.newLuaState();
    luaState.pushLuaString(LuaString.newLuaString("value"));
    luaState.replace(1);
    // 注意这里可能有点争议，当元素只剩1个时，replace的行为应该是什么？
    assertEquals(0, luaState.getTop());
    //    assertTrue(luaState.isLuaString(1));
    //    assertEquals(LuaValue.of("value"), luaState.toLuaString(1));
  }

  @Test
  void replaceStackSize2() {
    LuaState luaState = LuaState.newLuaState();
    luaState.pushLuaString(LuaString.newLuaString("value"));
    luaState.pushLuaNil();
    luaState.replace(1);
    assertEquals(1, luaState.getTop());
    assertTrue(luaState.isLuaNil(1));
  }

  @Test
  void testNextMeetException() {
    LuaState luaState = LuaState.newLuaState();
    assertThrows(IllegalStateException.class, () -> luaState.next(-1));
  }
}
