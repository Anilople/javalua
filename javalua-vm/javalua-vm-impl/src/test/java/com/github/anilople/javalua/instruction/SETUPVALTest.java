package com.github.anilople.javalua.instruction;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.api.LuaVMTestImpl;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.chunk.Upvalue;
import com.github.anilople.javalua.state.LuaClosure;
import com.github.anilople.javalua.state.LuaUpvalue;
import com.github.anilople.javalua.state.LuaValue;
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

    LuaClosure luaClosure = new LuaClosure(prototype);
    final LuaValueWrapper luaValueWrapper = new LuaValueWrapper(null);
    {
      LuaUpvalue luaUpvalue =
          LuaUpvalue.newLuaUpvalue(luaValueWrapper::getLuaValue, luaValueWrapper::setLuaValue);
      luaClosure.setLuaUpvalue(0, luaUpvalue);
    }

    final LuaValue expectedLuaValue = LuaValue.of(999L);
    luaVM.pushCallFrameForPrototype(luaClosure, new LuaValue[0]);

    luaVM.topCallFrame().set(4, expectedLuaValue);

    setupval.applyTo(luaVM);

    LuaVM.printLuaVM(luaVM);

    assertEquals(expectedLuaValue, luaClosure.getLuaUpvalue(0).getLuaValue());
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
