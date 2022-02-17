package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;

class NOT extends AbstractInstruction {
  NOT(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var value = luaState.toLuaBoolean(operand.B() + 1);
    luaState.pushLuaBoolean(value);
    luaState.replace(operand.A() + 1);
  }
}
