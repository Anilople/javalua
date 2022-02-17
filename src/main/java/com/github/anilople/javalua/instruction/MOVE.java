package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class MOVE extends AbstractInstruction {
  MOVE(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var a = operand.A();
    var b = operand.B();
    luaState.copy(b + 1, a + 1);
  }
}
