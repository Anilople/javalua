package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 和{@link GETTABUP}对应
 *
 * UpValue[A][RK(B)] := RK(C)
 */
class SETTABUP extends UpvalueInstruction {
  SETTABUP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = operand.A() + 1;
    int b = operand.B();
    int c = operand.C();
    luaVM.getRK(b);
    luaVM.getRK(c);

    int index = luaUpvalueIndex(aIndex);
    luaVM.setTable(index);
  }
}
