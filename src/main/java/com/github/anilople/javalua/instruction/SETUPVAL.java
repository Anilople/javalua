package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 与{@link GETUPVAL}对应
 *
 * UpValue[B] := R(A)
 */
class SETUPVAL extends UpvalueInstruction {
  SETUPVAL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = operand.A() + 1;
    int bIndex = operand.B();

    int index = luaUpvalueIndex(bIndex);
    luaVM.copy(aIndex, index);
  }
}
