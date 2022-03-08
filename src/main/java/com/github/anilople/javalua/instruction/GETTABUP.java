package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * page 197
 *
 * R(A) := UpValue[B][RK(C)]
 */
class GETTABUP extends UpvalueInstruction {
  GETTABUP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = operand.A() + 1;
    int bIndex = operand.B() + 1;

    int c = operand.C();

    luaVM.getRK(c);

    int index = luaUpvalueIndex(bIndex);
    luaVM.getTable(index);

    luaVM.replace(aIndex);
  }
}
