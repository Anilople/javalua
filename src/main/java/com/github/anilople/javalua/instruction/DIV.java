package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.state.LuaState;

class DIV extends ArithmeticInstruction {
  DIV(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    binaryArithmeticOperator(luaState, ArithmeticOperator.LUA_OPDIV);
  }
}
