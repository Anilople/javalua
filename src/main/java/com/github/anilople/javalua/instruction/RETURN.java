package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 把存放在连续多个寄存器里的值返回给主调函数
 *
 * 对应Lua脚本的return语句
 */
class RETURN extends FunctionInstruction {
  RETURN(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = operand.A() + 1;
    int registerCount = operand.B() - 1;
    if (registerCount == 0) {

    } else if (registerCount > 0) {
      luaVM.checkStack(registerCount);
      for (int offset = 0; offset < registerCount; offset++) {
        int index = aIndex + offset;
        luaVM.pushValue(index);
      }
    } else {
      fixStack(aIndex, luaVM);
    }
  }
}
