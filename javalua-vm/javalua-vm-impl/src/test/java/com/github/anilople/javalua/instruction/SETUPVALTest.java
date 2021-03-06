package com.github.anilople.javalua.instruction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.api.LuaVMTestImpl;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.chunk.Upvalue;
import com.github.anilople.javalua.state.*;
import com.github.anilople.javalua.state.LuaClosure;
import com.github.anilople.javalua.state.LuaUpvalue;
import lombok.Data;
import org.junit.jupiter.api.Test;

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
    LuaVMTestImpl luaVM = new LuaVMTestImpl(10, prototype);

    LuaClosure luaClosure = LuaClosure.newPrototypeLuaClosure(prototype);
    final LuaValueWrapper luaValueWrapper = new LuaValueWrapper(null);
    {
      LuaUpvalue luaUpvalue =
          LuaUpvalue.newLuaUpvalue(luaValueWrapper::getLuaValue, luaValueWrapper::setLuaValue);
      luaClosure.setLuaUpvalue(0, luaUpvalue);
    }

    final LuaValue expectedLuaValue = LuaInteger.newLuaInteger(999L);
    luaVM.pushCallFrameForPrototype(luaClosure, new LuaValue[0]);

    luaVM.topCallFrame().set(4, expectedLuaValue);

    setupval.applyTo(luaVM);

    LuaVM.printLuaVM(luaVM);

    assertEquals(expectedLuaValue, luaClosure.getLuaValue(0));
    assertEquals(expectedLuaValue, luaValueWrapper.luaValue);
  }

  @Data
  static class LuaValueWrapper {
    LuaValue luaValue;

    public LuaValueWrapper(LuaValue luaValue) {
      this.luaValue = luaValue;
    }
  }
}
