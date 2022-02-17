package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.state.LuaState;

class SHR extends BitwiseInstruction {
  SHR(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    binaryBitwiseOperator(luaState, BitwiseOperator.LUA_OPSHR);
  }
}
