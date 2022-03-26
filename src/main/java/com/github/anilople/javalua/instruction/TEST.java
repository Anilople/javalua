package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.LuaBoolean;
import com.github.anilople.javalua.state.LuaValue;

/**
 * page 109
 *
 * <pre>
 *   {@code
 *    if not (R(A) == c) {
 *      then pc++
 *    }
 *   }
 * </pre>

 */
class TEST extends AbstractInstruction {
  TEST(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    final var aIndex = operand.A() + 1;
    final int c = operand.C();
    final LuaBoolean cBooleanValue;
    if (c == 0) {
      cBooleanValue = LuaValue.FALSE;
    } else if (c == 1) {
      cBooleanValue = LuaValue.TRUE;
    } else {
      throw new IllegalStateException("c = " + c);
    }
    var aBooleanValue = luaVM.toLuaBoolean(aIndex);
    if (!cBooleanValue.equals(aBooleanValue)) {
      luaVM.addPC(1);
    }
  }
}
