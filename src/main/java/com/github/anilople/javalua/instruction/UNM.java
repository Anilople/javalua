package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.state.LuaState;

class UNM extends ArithmeticInstruction {
  UNM(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var a = operand.A();
    var b = operand.B();
    luaState.pushValue(b + 1);
    luaState.arithmetic(ArithmeticOperator.LUA_OPUNM);
    luaState.replace(a + 1);
  }
}
