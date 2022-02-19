package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaValue;

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
    var startIndex = operand.A() + 1;
    var length = operand.B();
    var c = operand.C();

    if (c > 0) {
      c = c - 1;
    } else {
      var extraarg = luaVM.fetch();
      c = extraarg.getOperand().Ax();
    }

    var index = c * LFIELDS_PER_FLUSH;
    for (int j = 1; j <= length; j++) {
      luaVM.pushValue(startIndex + j);
      LuaInteger indexKey = LuaValue.of(index + j);
      luaVM.setI(startIndex, indexKey);
    }
  }
}
