package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class GETUPVAL extends AbstractInstruction {
  GETUPVAL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    throw new UnsupportedOperationException();
  }
}
