package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * 尾递归。避免1次函数调用，就新增一个{@link com.github.anilople.javalua.state.CallFrame}进而导致栈溢出
 *
 * {@link return f(args)}这样的返回语句会被Lua编译器编译成{@link TAILCALL}指令
 */
class TAILCALL extends FunctionInstruction {
  TAILCALL(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    int aIndex = operand.A() + 1;
    int b = operand.B();
    int nArgs = pushFuncAndArgs(aIndex, b, luaVM);
    luaVM.call(nArgs, -1);
    popResults(aIndex, 0, luaVM);
    // TODO, 优化
  }
}
