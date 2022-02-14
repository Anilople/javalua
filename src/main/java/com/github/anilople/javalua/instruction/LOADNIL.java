package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class LOADNIL extends AbstractInstruction {
  LOADNIL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var a = operand.A();
    var b = operand.B();
    var start = a + 1;
    var length = b;

    luaVM.pushLuaNil();

    for (int i = 0; i < length; i++) {
      luaVM.copy(-1, start + i);
    }

    luaVM.pop(1);
  }
}
