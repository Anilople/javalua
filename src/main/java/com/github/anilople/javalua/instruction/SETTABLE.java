package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class SETTABLE extends AbstractInstruction {
  SETTABLE(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    throw new UnsupportedOperationException();
  }
}
