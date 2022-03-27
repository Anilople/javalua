package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class MOVE extends AbstractInstruction {
  MOVE(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var a = operand.A();
    var b = operand.B();
    luaVM.copy(b + 1, a + 1);
  }

  @Override
  public String toString() {
    int aIndex = operand.A() + 1;
    int bIndex = operand.B() + 1;
    String mid = "copy " + bIndex + " to " + aIndex;
    return this.toStringHelper(mid);
  }
}
