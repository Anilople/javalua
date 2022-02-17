package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class CALL extends FunctionInstruction {
  CALL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    throw new UnsupportedOperationException();
  }
}
