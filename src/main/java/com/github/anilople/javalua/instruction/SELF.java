package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 优化方法调用的语法糖。
 *
 * 把对象和方法拷贝到相邻的两个目标寄存器中。
 *
 * 对象在寄存器中，索引由操作数B指定。
 *
 * 方法名在常量表里，索引由操作数C指定。
 *
 * 目标寄存器索引由操作数A指定
 */
class SELF extends FunctionInstruction {
  SELF(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    final int objectIndex = operand.B() + 1;
    final int methodIndexInConstants = operand.C();
    final int targetRegisterIndex = operand.A() + 1;

    // 获取方法名
    luaVM.getRK(methodIndexInConstants);
    // 根据方法名，从对象中获取方法
    luaVM.getTable(objectIndex);
    // 把方法放到目标寄存器
    luaVM.replace(targetRegisterIndex);

    // 把对象放到目标寄存器
    luaVM.copy(objectIndex, targetRegisterIndex + 1);
  }
}
