package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class LEN extends AbstractInstruction {
  LEN(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var a = operand.A();
    var b = operand.B();
    luaVM.len(b + 1);
    luaVM.replace(a + 1);
  }
}
