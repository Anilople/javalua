package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class GETTABUP extends AbstractInstruction {
  GETTABUP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    throw new UnsupportedOperationException();
  }
}
