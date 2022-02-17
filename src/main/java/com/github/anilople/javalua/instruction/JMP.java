package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class JMP extends AbstractInstruction {
  JMP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var a = operand.A();
    var sBx = operand.sBx();
    luaState.addPC(sBx);
    if (0 != a) {
      throw new UnsupportedOperationException("todo，第6章 虚拟机雏形 推迟到第10章");
    }
  }
}
