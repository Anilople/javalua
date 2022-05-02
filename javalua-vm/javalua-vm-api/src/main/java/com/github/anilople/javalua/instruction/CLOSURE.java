package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 把当前Lua函数的子函数原型实例化为闭包，放入由操作数A指定的寄存器中
 *
 * R(A) := closure(KPROTO[Bx])
 */
class CLOSURE extends FunctionInstruction {
  CLOSURE(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var aIndex = operand.A() + 1;
    var bx = operand.Bx();

    luaVM.loadPrototype(bx);
    luaVM.replace(aIndex);
  }
}
