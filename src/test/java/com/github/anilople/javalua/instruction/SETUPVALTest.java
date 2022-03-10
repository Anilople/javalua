package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.DefaultLuaVMTestImpl;
import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.chunk.Upvalue;
import com.github.anilople.javalua.instruction.Instruction.Opcode;
import com.github.anilople.javalua.instruction.Instruction.Operand;
import com.github.anilople.javalua.state.CallFrame;
import com.github.anilople.javalua.state.LuaClosure;
import com.github.anilople.javalua.state.LuaUpvalue;
import com.github.anilople.javalua.state.LuaValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wxq
 */
class SETUPVALTest {

  @Test
  void applyTo() {
    int value = Opcode.getOpcodeValueOf(Opcode.SETUPVAL);
    Operand operand = Operand.iABC(0, 0, 3);
    value |= operand.getCodeValue();
    SETUPVAL setupval = new SETUPVAL(value);

    Prototype prototype = new Prototype();
    prototype.setUpvalues(new Upvalue[3]);
    prototype.setMaxStackSize((byte) 5);
    DefaultLuaVMTestImpl luaVM = new DefaultLuaVMTestImpl(10, prototype);

    LuaClosure luaClosure = new LuaClosure(prototype);
    final LuaValue expectedLuaValue = LuaValue.of(999L);
    luaVM.pushCallFrameForPrototype(luaClosure, new LuaValue[0]);

    luaVM.topCallFrame().set(4, expectedLuaValue);

    setupval.applyTo(luaVM);

    LuaVM.printLuaVM(luaVM);

    assertEquals(expectedLuaValue, luaClosure.getLuaUpvalue(0).getLuaValue());
  }
}