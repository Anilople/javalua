package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * page 133
 *
 * 根据key从表里取值
 *
 *  R(A) := R(B)[RK(C)]
 */
class GETTABLE extends TableInstruction {
  GETTABLE(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var aIndex = operand.A() + 1;
    var bIndex = operand.B() + 1;
    var c = operand.C();
    luaVM.getRK(c);
    luaVM.getTable(bIndex);
    luaVM.replace(aIndex);
  }

  @Override
  public String toString() {
    var aIndex = operand.A() + 1;
    var bIndex = operand.B() + 1;
    var c = operand.C();
    return this.getClass().getSimpleName() + String.format(" R(%d) = R(%d)[RK(%d)] ", aIndex, bIndex, c);
  }
}
