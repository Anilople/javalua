package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaValue;

class TEST extends AbstractInstruction {
  TEST(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    final var aIndex = operand.A() + 1;
    var aBooleanValue = luaState.toLuaBoolean(aIndex);
    var expect = LuaValue.of(operand.C() != 0);
    if (aBooleanValue.equals(expect)) {
      luaState.addPC(1);
    }
  }
}
