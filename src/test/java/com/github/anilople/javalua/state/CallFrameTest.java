package com.github.anilople.javalua.state;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.chunk.Prototype;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class CallFrameTest {

  @Test
  void testStackOverFlow() {
    CallFrame callFrame = new CallFrame(0, new Prototype());
    assertThrows(IllegalStateException.class, () -> callFrame.push(LuaValue.NIL));
  }

  @Test
  void testPushNCase1() {
    CallFrame callFrame = new CallFrame(6, new Prototype());
    LuaValue[] luaValues =
        new LuaValue[] {
          LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"),
        };
    callFrame.pushN(luaValues);
    assertEquals(LuaValue.of("abc"), callFrame.pop());
    assertEquals(LuaValue.of(3L), callFrame.pop());
    assertEquals(LuaValue.NIL, callFrame.pop());
  }

  @Test
  void testPushNCase2() {
    CallFrame callFrame = new CallFrame(6, new Prototype());
    LuaValue[] luaValues =
        new LuaValue[] {
          LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"), LuaValue.of("3fx"),
        };
    callFrame.pushN(luaValues, 2);
    assertEquals(LuaValue.of(3L), callFrame.pop());
    assertEquals(LuaValue.NIL, callFrame.pop());
  }

  @Test
  @DisplayName("pushN 希望push的数量是负数 push所有")
  void testPushNCase3() {
    CallFrame callFrame = new CallFrame(6, new Prototype());
    LuaValue[] luaValues =
        new LuaValue[] {
            LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"), LuaValue.of("3fx"),
        };
    callFrame.pushN(luaValues, -1);
    assertEquals(luaValues[3], callFrame.pop());
    assertEquals(luaValues[2], callFrame.pop());
    assertEquals(luaValues[1], callFrame.pop());
    assertEquals(luaValues[0], callFrame.pop());
  }

  @Test
  void testPopNCase1() {
    CallFrame callFrame = new CallFrame(6, new Prototype());
    {
      LuaValue[] luaValues =
          new LuaValue[] {
            LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"), LuaValue.of("3fx"),
          };
      callFrame.pushN(luaValues);
    }
    assertEquals(0, callFrame.popN(0).length);
    LuaValue[] luaValues = callFrame.popN(2);
    assertEquals(2, luaValues.length);
    assertEquals(LuaValue.of("3fx"), luaValues[0]);
    assertEquals(LuaValue.of("abc"), luaValues[1]);
  }

  @Test
  void testPushPopEquals() {
    CallFrame callFrame = new CallFrame(6, new Prototype());
    LuaValue[] luaValuesForPush =
        new LuaValue[] {
          LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"), LuaValue.of("3fx"),
        };
    callFrame.pushN(luaValuesForPush);
    LuaValue[] luaValuesPopped = callFrame.popN(luaValuesForPush.length);
    assertEquals(luaValuesForPush.length, luaValuesPopped.length);
    for (int i = 0; i < luaValuesForPush.length; i++) {
      int j = luaValuesForPush.length - i - 1;
      assertEquals(luaValuesForPush[i], luaValuesPopped[j]);
    }
  }
}
