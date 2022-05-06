package com.github.anilople.javalua.instruction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.api.LuaVMTestImpl;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.chunk.Upvalue;
import com.github.anilople.javalua.state.*;
import com.github.anilople.javalua.state.CallFrame;
import com.github.anilople.javalua.state.LuaClosure;
import com.github.anilople.javalua.state.LuaUpvalue;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class GETUPVALTest {

  @Test
  void applyTo() {
    int value = Opcode.getOpcodeValueOf(Opcode.GETUPVAL);
    Operand operand = Operand.builder().iABC(1, 0, 3).build();
    value |= operand.getCodeValue();
    GETUPVAL getupval = new GETUPVAL(value);

    Prototype prototype = new Prototype();
    prototype.setUpvalues(new Upvalue[3]);
    prototype.setMaxStackSize((byte) 5);
    LuaVMTestImpl luaVM = new LuaVMTestImpl(10, prototype);

    LuaClosure luaClosure = LuaClosure.newPrototypeLuaClosure(prototype);
    final LuaValue expectedLuaValue = LuaInteger.newLuaInteger(999L);
    LuaUpvalue luaUpvalue = LuaUpvalue.newLuaUpvalue(() -> expectedLuaValue, luaValue -> {});
    luaClosure.setLuaUpvalue(1, luaUpvalue);

    luaVM.pushCallFrameForPrototype(luaClosure, new LuaValue[0]);

    getupval.applyTo(luaVM);

    LuaVM.printLuaVM(luaVM);

    CallFrame callFrame = luaVM.topCallFrame();
    assertEquals(expectedLuaValue, callFrame.get(4));
  }
}
