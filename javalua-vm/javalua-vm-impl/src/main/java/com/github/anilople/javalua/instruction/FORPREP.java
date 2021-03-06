package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;

class FORPREP extends FOR {
  FORPREP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    final var aIndex = operand.A() + 1;
    final var sBx = operand.sBx();

    // R(A) -= R(A+2)
    luaVM.pushValue(aIndex);
    luaVM.pushValue(aIndex + 2);
    luaVM.arithmetic(ArithmeticOperator.LUA_OPSUB);
    luaVM.replace(aIndex);

    // pc += sBx
    luaVM.addPC(sBx);
  }
}
