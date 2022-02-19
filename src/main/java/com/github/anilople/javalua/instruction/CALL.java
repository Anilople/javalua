package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 调用Lua函数
 * <p>
 * 被调函数在寄存器中，索引由操作数A指定
 * <p>
 * 被调函数的参数，紧挨着被调函数，数量由操作数B指定
 * <p>
 * 返回值的数量由操作数C指定，
 * <p>
 * 由于lua在设计上可以接受多个返回值， 例如 {@code f(1, 2, g())}，无论g有几个返回值，f都会接收，用 特殊值 0来处理这种情况， 此时 g的操作数C =
 * 0，f的操作数B=0
 */
class CALL extends FunctionInstruction {

  CALL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    final var aIndex = operand.A() + 1;
    final var argsAmount = operand.B();
    int resultsAmount = operand.C();
    final var nArgs = pushFuncAndArgs(aIndex, argsAmount, luaVM);
    luaVM.call(nArgs, resultsAmount);
    popResults(aIndex, resultsAmount, luaVM);
  }
}
