package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;

class BNOT extends AbstractInstruction {
  BNOT(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var a = operand.A();
    var b = operand.B();
    luaVM.pushValue(b + 1);
    luaVM.bitwise(BitwiseOperator.LUA_OPBNOT);
    luaVM.replace(a + 1);
  }
}
