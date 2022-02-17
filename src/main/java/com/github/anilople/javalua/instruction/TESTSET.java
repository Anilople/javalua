package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaValue;

class TESTSET extends AbstractInstruction {
  TESTSET(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    final var aIndex = operand.A() + 1;
    final var bIndex = operand.B() + 1;
    var bBooleanValue = luaState.toLuaBoolean(bIndex);
    var expect = LuaValue.of(operand.C() != 0);
    if (bBooleanValue.equals(expect)) {
      luaState.copy(bIndex, aIndex);
    } else {
      luaState.addPC(1);
    }
  }
}
