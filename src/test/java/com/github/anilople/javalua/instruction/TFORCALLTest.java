package com.github.anilople.javalua.instruction;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction.Opcode;
import com.github.anilople.javalua.instruction.Instruction.Operand;
import com.github.anilople.javalua.state.JavaFunction;
import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaValue;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class TFORCALLTest {

  @Test
  void applyTo() {
    final int A = 1;
    final TFORCALL tforcall;
    {
      int value = Opcode.getOpcodeValueOf(Opcode.SETUPVAL);
      Operand operand = Operand.iABC(0, 2, A);
      value |= operand.getCodeValue();
      tforcall = new TFORCALL(value);
    }
    Prototype prototype = new Prototype();
    prototype.setMaxStackSize((byte) 20);
    LuaVM luaVM = LuaVM.create(20, new Prototype());

    // R(A-1)
    luaVM.pushLuaNil();
    // R(A)
    {
      JavaFunction javaFunction =
          new JavaFunction() {
            @Override
            public void registerTo(LuaVM luaVM) {
              luaVM.register(LuaValue.of("temp-for-TFORCALL"), this);
            }

            @Override
            public Integer apply(LuaState luaState) {
              luaState.pop(2);
              luaState.pushLuaInteger(LuaValue.of(999L));
              return 1;
            }
          };
      luaVM.pushJavaFunction(javaFunction);
    }
    // R(A+1)
    luaVM.pushLuaInteger(LuaValue.of(111L));
    // R(A+2)
    luaVM.pushLuaInteger(LuaValue.of(222L));
    // dummy
    luaVM.setTop(10);

    tforcall.applyTo(luaVM);
    assertTrue(luaVM.isLuaNil(A + 5));
    assertTrue(luaVM.isLuaInteger(A + 4));
    assertEquals(LuaValue.of(999L), luaVM.toLuaInteger(A + 4));
  }
}
