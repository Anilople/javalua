package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;

class UNM extends ArithmeticInstruction {
  UNM(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var a = operand.A();
    var b = operand.B();
    luaVM.pushValue(b + 1);
    luaVM.arithmetic(ArithmeticOperator.LUA_OPUNM);
    luaVM.replace(a + 1);
  }
}
