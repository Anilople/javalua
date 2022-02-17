package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class RETURN extends FunctionInstruction {
  RETURN(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    throw new UnsupportedOperationException();
  }
}
