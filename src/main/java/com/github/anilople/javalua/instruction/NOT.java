package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

class NOT extends AbstractInstruction {
  NOT(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var value = luaVM.toLuaBoolean(operand.B() + 1);
    luaVM.pushLuaBoolean(value);
    luaVM.replace(operand.A() + 1);
  }
}
