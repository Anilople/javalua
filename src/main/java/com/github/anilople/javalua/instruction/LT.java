package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.state.LuaState;

class LT extends ComparisonInstruction {
  LT(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    compare(luaState, ComparisonOperator.LUA_OPLT);
  }
}
