package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * page 229
 *
 * R(A+2+1), R(A+2+2), ... , R(A+2+C) := R(A) (R(A+1), R(A+2))
 */
class TFORCALL extends FOR {
  TFORCALL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = this.operand.A() + 1;
    int c = this.operand.C();
    FunctionInstruction.pushFuncAndArgs(aIndex, 3, luaVM);
    luaVM.call(2, c);
    FunctionInstruction.popResults(aIndex + 3, c, luaVM);
  }
}
