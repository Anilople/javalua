package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaValue;

class FORLOOP extends AbstractInstruction {
  FORLOOP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaState luaState) {
    var aIndex = operand.A() + 1;

    // R(A) += R(A+2)
    luaState.pushValue(aIndex + 2);
    luaState.pushValue(aIndex);
    luaState.arithmetic(ArithmeticOperator.LUA_OPADD);
    luaState.replace(aIndex);

    // R(A) <?= R(A+1)
    var luaNumber = luaState.toLuaNumber(aIndex);
    if (luaNumber.isPositive()) {
      // 当步长是正数时，符号 <?= 的含义是 <= 也就是继续循环的条件是数值不大于限制
      var result = luaState.compare(aIndex, aIndex + 1, ComparisonOperator.LUA_OPLE);
      if (LuaValue.TRUE.equals(result)) {
        addPcAndCopy(luaState, operand.sBx(), aIndex);
      }
    } else {
      // 当步长是负数时，符号 <?= 的含义是 >= 循环继续的条件就变成了数值不小于限制
      var result = luaState.compare(aIndex + 1, aIndex, ComparisonOperator.LUA_OPLT);
      if (LuaValue.TRUE.equals(result)) {
        addPcAndCopy(luaState, operand.sBx(), aIndex);
      }
    }
  }

  void addPcAndCopy(LuaState luaState, int sBx, int aIndex) {
    luaState.addPC(sBx);
    luaState.copy(aIndex, aIndex + 3);
  }
}
