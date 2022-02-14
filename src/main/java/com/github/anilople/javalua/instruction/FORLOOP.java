package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.state.LuaBoolean;
import com.github.anilople.javalua.state.LuaValue;

class FORLOOP extends AbstractInstruction {
  FORLOOP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var aIndex = operand.A() + 1;

    // R(A) += R(A+2)
    luaVM.pushValue(aIndex + 2);
    luaVM.pushValue(aIndex);
    luaVM.arithmetic(ArithmeticOperator.LUA_OPADD);
    luaVM.replace(aIndex);

    // R(A) <?= R(A+1)
    var luaNumber = luaVM.toLuaNumber(aIndex);
    if (luaNumber.isPositive()) {
      // 当步长是正数时，符号 <?= 的含义是 <= 也就是继续循环的条件是数值不大于限制
      var result = luaVM.compare(aIndex, aIndex + 1, ComparisonOperator.LUA_OPLE);
      if (LuaValue.TRUE.equals(result)) {
        addPcAndCopy(luaVM, operand.sBx(), aIndex);
      }
    } else {
      // 当步长是负数时，符号 <?= 的含义是 >= 循环继续的条件就变成了数值不小于限制
      var result = luaVM.compare(aIndex + 1, aIndex, ComparisonOperator.LUA_OPLT);
      if (LuaValue.TRUE.equals(result)) {
        addPcAndCopy(luaVM, operand.sBx(), aIndex);
      }
    }
  }

  void addPcAndCopy(LuaVM luaVM, int sBx, int aIndex) {
    luaVM.addPC(sBx);
    luaVM.copy(aIndex, aIndex + 3);
  }
}
