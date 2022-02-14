package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;

class BXOR extends AbstractInstruction {
  BXOR(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    binaryBitwiseOperator(luaVM, BitwiseOperator.LUA_OPBXOR);
  }
}
