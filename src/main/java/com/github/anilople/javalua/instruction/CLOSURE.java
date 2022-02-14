package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class CLOSURE extends AbstractInstruction {
  CLOSURE(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    throw new UnsupportedOperationException();
  }
}
