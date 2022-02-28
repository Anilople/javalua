package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class GETTABUP extends AbstractInstruction {
  GETTABUP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = operand.A() + 1;
    int c = operand.C();

    luaVM.pushGlobalTable();
    luaVM.getRK(c);
    luaVM.getTable(-2);
    luaVM.replace(aIndex);
    // pop global table
    luaVM.pop(1);
  }
}
