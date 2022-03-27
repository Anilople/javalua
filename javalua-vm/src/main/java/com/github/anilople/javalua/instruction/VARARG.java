package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.CallFrame;

/**
 * 把传递给当前函数的变长参数加载到连续多个寄存器中
 * <p>
 * 由于vararg的数量在{@link CallFrame#getVarargs()}中已经知道， 但是实际不一定用到这么多，所以用操作数B=0表示需要用所有的参数， 用 操作数B - 1
 * 表示实际需要用多少个参数
 * <p/>
 * 参考 C语言实现 <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lvm.c#L1295">vmcase(OP_VARARG)</a>
 */
class VARARG extends FunctionInstruction {

  VARARG(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = operand.A() + 1;
    int requiredResults = operand.B() - 1;
    if (0 != requiredResults) {
      luaVM.loadVararg(requiredResults);
      popResults(aIndex, requiredResults, luaVM);
    }
  }
}
