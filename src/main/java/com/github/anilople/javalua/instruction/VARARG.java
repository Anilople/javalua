package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.CallFrame;

/**
 * 把传递给当前函数的变长参数加载到连续多个寄存器中
 * <p>
 * 由于vararg的数量在{@link CallFrame#getVarargs()}中已经知道， 但是实际不一定用到这么多，所以用操作数B=0表示需要用所有的参数， 用 操作数B - 1
 * 表示实际需要用多少个参数
 */
class VARARG extends FunctionInstruction {

  VARARG(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = operand.A() + 1;
    int howManyNeedToCopy = operand.B() - 1;
    if (howManyNeedToCopy > 0) {
      luaVM.loadVararg(howManyNeedToCopy);
      popResults(aIndex, howManyNeedToCopy, luaVM);
    } else if (howManyNeedToCopy == 0) {

    } else {
      // all
      luaVM.loadVararg(-1);
      popResults(aIndex, -1, luaVM);
    }
  }
}
