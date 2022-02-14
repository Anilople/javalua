package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;

class SUB extends AbstractInstruction {
  SUB(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    binaryArithmeticOperator(luaVM, ArithmeticOperator.LUA_OPSUB);
  }
}
