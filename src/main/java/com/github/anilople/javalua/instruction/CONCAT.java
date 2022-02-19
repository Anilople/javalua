package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class CONCAT extends AbstractInstruction {
  CONCAT(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var start = operand.B() + 1;
    var end = operand.C() + 1;

    var n = end - start + 1;
    luaVM.checkStack(n);
    for (int i = start; i <= end; i++) {
      luaVM.pushValue(i);
    }

    luaVM.concat(n);
    luaVM.replace(operand.A() + 1);
  }
}
