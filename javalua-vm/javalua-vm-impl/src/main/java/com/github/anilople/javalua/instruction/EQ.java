package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;

class EQ extends ComparisonInstruction {
  EQ(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    compare(luaVM, ComparisonOperator.LUA_OPEQ);
  }
}
