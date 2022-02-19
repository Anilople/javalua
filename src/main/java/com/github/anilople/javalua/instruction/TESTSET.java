package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaValue;

class TESTSET extends AbstractInstruction {
  TESTSET(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    final var aIndex = operand.A() + 1;
    final var bIndex = operand.B() + 1;
    var bBooleanValue = luaVM.toLuaBoolean(bIndex);
    var expect = LuaValue.of(operand.C() != 0);
    if (bBooleanValue.equals(expect)) {
      luaVM.copy(bIndex, aIndex);
    } else {
      luaVM.addPC(1);
    }
  }
}
