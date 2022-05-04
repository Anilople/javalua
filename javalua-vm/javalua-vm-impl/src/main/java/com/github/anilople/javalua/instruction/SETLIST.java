package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaInteger;

/**
 * 给数组准备，按索引批量设置数组元素
 */
class SETLIST extends AbstractInstruction {

  private static final int LFIELDS_PER_FLUSH = 50;

  SETLIST(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    // 数组所在的位置
    final int indexOfArray = operand.A() + 1;
    final int length = operand.B();
    // 数组起始索引
    var c = operand.C();

    if (c > 0) {
      c = c - 1;
    } else {
      var extraarg = luaVM.fetch();
      c = extraarg.getOperand().Ax();
    }

    final int beginIndexInArray = c * LFIELDS_PER_FLUSH;
    if (length == 0) {
      // 使用留在栈顶的全部返回值
      int newLength = (int) luaVM.toLuaInteger(-1).getJavaValue() - indexOfArray - 1;
      luaVM.pop(1);
      setI(luaVM, indexOfArray, beginIndexInArray, newLength);
      final int argsAmount = luaVM.getTop() - luaVM.getRegisterCount();
      for (int offset = 1; offset <= argsAmount; offset++) {
        final int valueIndex = luaVM.getRegisterCount() + offset;
        luaVM.pushValue(valueIndex);
        var indexInArray = LuaInteger.newLuaInteger(beginIndexInArray + offset);
        luaVM.setI(indexOfArray, indexInArray);
      }
      // clear stack
      luaVM.setTop(luaVM.getRegisterCount());
    } else if (length > 0) {
      setI(luaVM, indexOfArray, beginIndexInArray, length);
    } else {
      throw new IllegalStateException("length = " + length);
    }
  }

  static void setI(
      LuaVM luaVM, final int indexOfArray, final int beginIndexInArray, final int length) {
    luaVM.checkStack(1);
    for (int j = 1; j <= length; j++) {
      luaVM.pushValue(indexOfArray + j);
      LuaInteger indexKey = LuaInteger.newLuaInteger(beginIndexInArray + j);
      luaVM.setI(indexOfArray, indexKey);
    }
  }
}
