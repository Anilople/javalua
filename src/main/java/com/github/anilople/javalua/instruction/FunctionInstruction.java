package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 和函数调用有关的指令
 *
 * @author wxq
 */
abstract class FunctionInstruction extends AbstractInstruction {

  public FunctionInstruction(int originCodeValue) {
    super(originCodeValue);
  }

  /**
   * page 155.
   * 把被调函数和参数值推入栈顶
   */
  static int pushFuncAndArgs(int aIndex, int argsAmount, LuaVM luaVM) {
    if (argsAmount >= 1) {
      luaVM.checkStack(argsAmount);
      for (int offset = 0; offset < argsAmount; offset++) {
        int index = aIndex + offset;
        luaVM.pushValue(index);
      }
      return argsAmount - 1;
    } else {
      // 函数的后半部分参数已经在栈顶了
      // 把函数和函数前半部分的返回值推入栈顶
      fixStack(aIndex, luaVM);
      return luaVM.getTop() - luaVM.getRegisterCount() - 1;
    }
  }

  /**
   * page 155.
   */
  static void popResults(int aIndex, int resultsAmount, LuaVM luaVM) {
    if (resultsAmount == 0) {
      // 没有返回值
    } else if (resultsAmount > 0) {
      // 把栈顶返回的值移动到相应寄存器
      for (int offset = resultsAmount - 1; offset >= 0; offset--) {
        int offsetIndex = aIndex + offset;
        luaVM.replace(offsetIndex);
      }
    } else {
      // 需要把被调函数的返回值全部返回
      luaVM.checkStack(1);
      // 把这些返回放在栈顶原封不动
      // push 1个整数 标记这些返回值原本要移动到哪些寄存器中
      luaVM.pushLuaInteger(LuaValue.of(aIndex));
    }
  }

  /**
   * page 156
   */
  static void fixStack(int aIndex, LuaVM luaVM) {
    final int length = (int) luaVM.toLuaInteger(-1).getValue() - aIndex;
    luaVM.pop(1);
    luaVM.checkStack(length);
    for (int offset = 0; offset < length; offset++) {
      int index = aIndex + offset;
      luaVM.pushValue(index);
    }
    luaVM.rotate(luaVM.getRegisterCount() + 1, length);
  }
}
