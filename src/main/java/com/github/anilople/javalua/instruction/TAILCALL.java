package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class TAILCALL extends FunctionInstruction {
  TAILCALL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    throw new UnsupportedOperationException();
  }
}
