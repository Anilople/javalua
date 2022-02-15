package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;

class IDIV extends ArithmeticInstruction {
  IDIV(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    binaryArithmeticOperator(luaVM, ArithmeticOperator.LUA_OPIDIV);
  }
}
