package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class LOADKX extends AbstractInstruction {
  LOADKX(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var a = operand.A();

    var instruction = luaState.fetch();
    var Ax = instruction.getOperand().Ax();
    luaState.getConst(Ax);

    luaState.replace(a + 1);
  }
}
