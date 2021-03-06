package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 常量加载到指定寄存器
 */
class LOADK extends AbstractInstruction {
  LOADK(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var a = operand.A();
    var Bx = operand.Bx();

    // 寄存器索引
    var registerIndex = a + 1;
    // 常量表索引
    var constantIndex = Bx;

    // 寄存器索引 可能就是栈顶
    luaVM.getConst(constantIndex);
    luaVM.replace(registerIndex);
  }
}
