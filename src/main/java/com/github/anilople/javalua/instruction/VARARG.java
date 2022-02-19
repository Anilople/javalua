package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 把传递给当前函数的变长参数加载到连续多个寄存器中
 */
class VARARG extends FunctionInstruction {
  VARARG(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = operand.A() + 1;
    int registerCount = operand.B();
    if (registerCount == 0) {

    } else if (registerCount > 0) {
      for (int offset = 0; offset < registerCount; offset++) {
        int index = aIndex + offset;
        luaVM.pushValue(index);
      }
    } else {
      throw new UnsupportedOperationException();
    }

  }
}
