package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * page 135
 *
 * 根据key往表里赋值
 *
 * R(A)[RK(B)] := RK(C)
 */
class SETTABLE extends TableInstruction {
  SETTABLE(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var aIndex = operand.A() + 1;
    var b = operand.B();
    var c = operand.C();

    luaVM.getRK(b);
    luaVM.getRK(c);
    luaVM.setTable(aIndex);
  }

  @Override
  public String toString() {
    var aIndex = operand.A() + 1;
    var b = operand.B();
    var c = operand.C();
    return this.getClass().getSimpleName()
        + String.format(" R(%d)[RK(%d)] = RK(%d) ", aIndex, b, c);
  }
}
