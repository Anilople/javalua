package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class CONCAT extends AbstractInstruction {
  CONCAT(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var start = operand.B() + 1;
    var end = operand.C() + 1;

    var n = end - start + 1;
    luaState.checkStack(n);
    for (int i = start; i <= end; i++) {
      luaState.pushValue(i);
    }

    luaState.concat(n);
    luaState.replace(operand.A() + 1);
  }
}
