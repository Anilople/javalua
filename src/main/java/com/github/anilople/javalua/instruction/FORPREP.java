package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.state.LuaState;

class FORPREP extends AbstractInstruction {
  FORPREP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    final var aIndex = operand.A() + 1;
    final var sBx = operand.sBx();

    // R(A) -= R(A+2)
    luaState.pushValue(aIndex);
    luaState.pushValue(aIndex + 2);
    luaState.arithmetic(ArithmeticOperator.LUA_OPSUB);
    luaState.replace(aIndex);

    // pc += sBx
    luaState.addPC(sBx);
  }
}
