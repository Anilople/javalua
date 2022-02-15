package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * page 103
 *
 * R(A) := length of R(B)
 */
class LEN extends AbstractInstruction {
  LEN(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var aIndex = operand.A() + 1;
    var bIndex = operand.B() + 1;
    luaVM.len(bIndex);
    luaVM.replace(aIndex);
  }

  @Override
  public String toString() {
    var aIndex = operand.A() + 1;
    var bIndex = operand.B() + 1;
    return this.getClass().getSimpleName()
        + String.format(" R(%d) := length of R(%d) ", aIndex, bIndex);
  }
}
