package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaInteger;

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
   * @param argsAmount 函数 1个 + 参数个数
   * @return 函数的入参个数
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
      // 例如 f(1, 2, g()) 需要把 g 的返回值，给 f 使用
      fixStack(aIndex, luaVM);
      return luaVM.getTop() - luaVM.getRegisterCount() - 1;
    }
  }

  /**
   * page 155.
   * @param resultsAmount 如果是-1，代表需要所有的返回值
   */
  static void popResults(int beginIndex, int resultsAmount, LuaVM luaVM) {
    if (resultsAmount == 0) {
      // 没有返回值
    } else if (resultsAmount > 0) {
      // 把栈顶返回的值移动到相应寄存器
      for (int offset = resultsAmount - 1; offset >= 0; offset--) {
        int offsetIndex = beginIndex + offset;
        luaVM.replace(offsetIndex);
      }
    } else {
      // 需要把被调函数的返回值全部返回
      luaVM.checkStack(1);
      // 把这些返回放在栈顶原封不动
      // push 1个整数 标记这些返回值原本要移动到哪些寄存器中
      luaVM.pushLuaInteger(LuaInteger.newLuaInteger(beginIndex));
    }
  }

  /**
   * page 156
   *
   * 把剩下的返回值也推入栈顶
   *
   * @param newBeginIndex 起始索引，由操作数 a 计算而来 参考 {@link #popResults}
   */
  static void fixStack(int newBeginIndex, LuaVM luaVM) {
    LuaInteger topValue = luaVM.toLuaInteger(-1);
    luaVM.pop(1);
    final int oldBeginIndex = (int) topValue.getJavaValue();
    final int length = oldBeginIndex - newBeginIndex;
    if (length < 0) {
      throw new IllegalStateException(
          "length = " + length + " top value = " + topValue + " begin index = " + newBeginIndex);
    }
    if (length == 0) {
      return;
    }
    luaVM.checkStack(length);
    for (int offset = 0; offset < length; offset++) {
      int index = newBeginIndex + offset;
      luaVM.pushValue(index);
    }
    luaVM.rotate(luaVM.getRegisterCount() + 1, length);
  }
}
