package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class LOADKX extends AbstractInstruction {
  LOADKX(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var a = operand.A();

    var instruction = luaVM.fetch();
    var Ax = instruction.getOperand().Ax();
    luaVM.getConst(Ax);

    luaVM.replace(a + 1);
  }
}
