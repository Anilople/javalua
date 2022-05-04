package com.github.anilople.javalua.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.chunk.Upvalue;
import com.github.anilople.javalua.state.*;
import com.github.anilople.javalua.state.CallFrame;
import com.github.anilople.javalua.state.LuaUpvalue;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class LuaVMImplTest {

  @Test
  void resolveLuaUpvalue_inStack_1() {
    Prototype prototype = new Prototype();
    prototype.setMaxStackSize((byte) 5);
    CallFrame callFrame = CallFrame.newCallFrame(10, prototype);
    final LuaValue expectedLuaValue = LuaInteger.newLuaInteger(99L);
    callFrame.set(1, expectedLuaValue);

    Upvalue upvalue = new Upvalue();
    upvalue.setInstack((byte) 1);
    upvalue.setIdx((byte) 0);

    LuaUpvalue luaUpvalue = LuaVMImpl.resolveLuaUpvalue(callFrame, upvalue);

    assertEquals(expectedLuaValue, luaUpvalue.getLuaValue());
  }

  @Test
  void resolveLuaUpvalue_inStack_3() {
    Prototype prototype = new Prototype();
    prototype.setMaxStackSize((byte) 5);
    CallFrame callFrame = CallFrame.newCallFrame(10, prototype);
    final LuaValue expectedLuaValue = LuaInteger.newLuaInteger(99L);
    callFrame.set(3, expectedLuaValue);

    Upvalue upvalue = new Upvalue();
    upvalue.setInstack((byte) 1);
    upvalue.setIdx((byte) 2);

    LuaUpvalue luaUpvalue = LuaVMImpl.resolveLuaUpvalue(callFrame, upvalue);

    assertEquals(expectedLuaValue, luaUpvalue.getLuaValue());
  }
}
