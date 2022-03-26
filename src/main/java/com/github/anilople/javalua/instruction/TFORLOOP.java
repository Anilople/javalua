package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * page 230
 *
 * <pre>
 *   {@code
 *   if R(A+1) ~= nil then {
 *     R(A) = R(A+1);
 *     pc += sBx
 *   }
 *   }
 * </pre>
 */
class TFORLOOP extends FOR {
  TFORLOOP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = this.operand.A() + 1;
    int sBx = this.operand.sBx();
    // R(A+1) 使用的是 operand.A() + 1 + 1
    if (!luaVM.isLuaNil(aIndex + 1)) {
      luaVM.copy(aIndex + 1, aIndex);
      luaVM.addPC(sBx);
    }
  }
}
