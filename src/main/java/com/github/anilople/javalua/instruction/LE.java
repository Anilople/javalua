package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;

class LE extends AbstractInstruction {
  LE(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    compare(luaVM, ComparisonOperator.LUA_OPLE);
  }
}
