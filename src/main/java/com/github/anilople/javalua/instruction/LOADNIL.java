package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class LOADNIL extends AbstractInstruction {
  LOADNIL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var a = operand.A();
    var b = operand.B();
    var start = a + 1;
    var length = b;

    luaState.pushLuaNil();

    for (int i = 0; i < length; i++) {
      luaState.copy(-1, start + i);
    }

    luaState.pop(1);
  }
}
