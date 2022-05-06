package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaBoolean;

class LOADBOOL extends AbstractInstruction {
  LOADBOOL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var a = operand.A();
    var b = operand.B();
    var c = operand.C();
    luaVM.pushLuaBoolean(LuaBoolean.newLuaBoolean(b != 0));

    var index = a + 1;
    luaVM.replace(index);

    if (c != 0) {
      luaVM.addPC(1);
    }
  }
}
