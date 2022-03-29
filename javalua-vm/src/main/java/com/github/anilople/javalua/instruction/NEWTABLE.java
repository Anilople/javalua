package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.util.FloatingPointByte;

/**
 * page 131
 *
 * R(A) := {} (size = B,C)
 *
 * B是nArr数组的大小
 *
 * C是nRec哈希表的大小
 */
class NEWTABLE extends TableInstruction {
  NEWTABLE(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var aIndex = operand.A() + 1;
    var b = operand.B();
    var c = operand.C();

    var arraySize = FloatingPointByte.decode(b);
    var mapSize = FloatingPointByte.decode(c);
    luaVM.createTable(arraySize, mapSize);
    luaVM.replace(aIndex);
  }

  @Override
  public String toString() {
    var aIndex = operand.A() + 1;
    var b = operand.B();
    var c = operand.C();
    return this.getClass().getSimpleName()
        + String.format(" R(%d) := {} (size = array(%d),map(%d)) ", aIndex, b, c);
  }
}
