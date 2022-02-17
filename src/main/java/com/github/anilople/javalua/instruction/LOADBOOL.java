package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaValue;

class LOADBOOL extends AbstractInstruction {
  LOADBOOL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var a = operand.A();
    var b = operand.B();
    var c = operand.C();
    luaState.pushLuaBoolean(LuaValue.of(b != 0));

    var index = a + 1;
    luaState.replace(index);

    if (c != 0) {
      luaState.addPC(1);
    }
  }
}
