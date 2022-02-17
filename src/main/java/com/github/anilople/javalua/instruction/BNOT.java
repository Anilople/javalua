package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.state.LuaState;

class BNOT extends BitwiseInstruction {
  BNOT(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var a = operand.A();
    var b = operand.B();
    luaState.pushValue(b + 1);
    luaState.bitwise(BitwiseOperator.LUA_OPBNOT);
    luaState.replace(a + 1);
  }
}
