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
    CallFrame callFrame = CallFrame.newCallFrame(0, new Prototype());
    assertThrows(IllegalStateException.class, () -> callFrame.push(LuaValue.NIL));
  }

  @Test
  void testPushNCase1() {
    CallFrame callFrame = CallFrame.newCallFrame(6, new Prototype());
    LuaValue[] luaValues =
        new LuaValue[] {
          LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"),
        };
    callFrame.pushN(luaValues);
    assertEquals(LuaValue.of("abc"), callFrame.pop());
    assertEquals(LuaValue.of(3L), callFrame.pop());
    assertTrue(callFrame.pop().isLuaNil());
  }

  @Test
  void testPushNCase2() {
    CallFrame callFrame = CallFrame.newCallFrame(6, new Prototype());
    LuaValue[] luaValues =
        new LuaValue[] {
          LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"), LuaValue.of("3fx"),
        };
    callFrame.pushN(luaValues, 2);
    assertEquals(LuaValue.of(3L), callFrame.pop());
    assertTrue(callFrame.pop().isLuaNil());
  }

  @Test
  @DisplayName("pushN 希望push的数量是负数 push所有")
  void testPushNCase3() {
    CallFrame callFrame = CallFrame.newCallFrame(6, new Prototype());
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
  void testPopNResultsCase1() {
    CallFrame callFrame = CallFrame.newCallFrame(6, new Prototype());
    {
      LuaValue[] luaValues =
          new LuaValue[] {
            LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"), LuaValue.of("3fx"),
          };
      callFrame.pushN(luaValues);
    }
    assertEquals(0, callFrame.popNResults(0).length);
    LuaValue[] luaValues = callFrame.popNResults(2);
    assertEquals(2, luaValues.length);
    assertEquals(LuaValue.of("3fx"), luaValues[0]);
    assertEquals(LuaValue.of("abc"), luaValues[1]);
  }

  @Test
  void popNArgs() {
    CallFrame callFrame = CallFrame.newCallFrame(6, new Prototype());
    LuaValue[] expectedLuaValues =
        new LuaValue[] {
          LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"), LuaValue.of("3fx"),
        };
    callFrame.pushN(expectedLuaValues);
    LuaValue[] luaValues = callFrame.popNArgs(expectedLuaValues.length);
    assertArrayEquals(expectedLuaValues, luaValues);
  }

  @Test
  void testPushPopEquals() {
    CallFrame callFrame = CallFrame.newCallFrame(6, new Prototype());
    LuaValue[] luaValuesForPush =
        new LuaValue[] {
          LuaValue.NIL, LuaValue.of(3L), LuaValue.of("abc"), LuaValue.of("3fx"),
        };
    callFrame.pushN(luaValuesForPush);
    LuaValue[] luaValuesPopped = callFrame.popNResults(luaValuesForPush.length);
    assertEquals(luaValuesForPush.length, luaValuesPopped.length);
    for (int i = 0; i < luaValuesForPush.length; i++) {
      int j = luaValuesForPush.length - i - 1;
      assertEquals(luaValuesForPush[i], luaValuesPopped[j]);
    }
  }
}
