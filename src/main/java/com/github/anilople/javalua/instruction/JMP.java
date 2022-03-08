package com.github.anilople.javalua.instruction;

import com.github.anilople.javalua.api.LuaVM;

/**
 * page 200 加入对upvalue的支持
 *
 * 兼顾着处于开启状态的Upvalue的责任。
 *
 * 如果某个块内部定义的局部变量已经被嵌套函数捕获，那么当这些局部变量退出作用域（也就是块结束）时，
 * 编译器会生成一条JMP指令，指示虚拟机闭合相应的Upvalue
 */
class JMP extends UpvalueInstruction {
  JMP(int originCodeValue) {
    super(originCodeValue);
  }

  @Override
  public void applyTo(LuaVM luaVM) {
    var a = operand.A();
    var sBx = operand.sBx();
    luaVM.addPC(sBx);
    if (0 != a) {
      luaVM.closeUpvalues(a);
      throw new UnsupportedOperationException("todo，第6章 虚拟机雏形 推迟到第10章");
    }
  }
}
