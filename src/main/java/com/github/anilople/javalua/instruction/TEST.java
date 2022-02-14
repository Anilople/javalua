package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaValue;

class TEST extends AbstractInstruction {
  TEST(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    final var aIndex = operand.A() + 1;
    var aBooleanValue = luaVM.toLuaBoolean(aIndex);
    var expect = LuaValue.of(operand.C() != 0);
    if (aBooleanValue.equals(expect)) {
      luaVM.addPC(1);
    }
  }
}
