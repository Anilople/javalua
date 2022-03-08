package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 把当前闭包的某个Upvalue值拷贝到目标寄存器中
 *
 * R(A) := UpValue[B]
 */
class GETUPVAL extends UpvalueInstruction {
  GETUPVAL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    // 目标寄存器的索引
    int aIndex = operand.A() + 1;
    // Upvalue索引由操作数B指定
    int bIndex = operand.B();
    // 操作数C没用

    int index = luaUpvalueIndex(bIndex);
    luaVM.copy(index, aIndex);
  }
}
