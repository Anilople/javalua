package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.state.LuaState;

/**
 * @author wxq
 */
public interface LuaVM extends LuaState {

  static LuaVM create(int stackSize, Prototype prototype) {
    return new DefaultLuaVMImpl(stackSize, prototype);
  }

  /**
   * 返回当前pc，不是必须的方法，仅测试使用
   */
  int pc();

  /**
   * 修改PC（用来实现跳转指令）
   */
  void addPC(int n);

  /**
   * 取出当前指令，将PC指向下一条指令
   * <p>
   * 虚拟机循环会使用，LOADKX等少数几个指令也会用到
   */
  Instruction fetch();

  /**
   * 将指定常量推入栈顶
   * <p>
   * LOADK和LOADKX会使用
   */
  void getConst(int index);

  /**
   * page 94
   * <p>
   * 将指定常量或栈值推入栈顶
   * <p>
   * 不是必须的方法，放这里是为了方便指令的实现，例如算术运算指令
   * <p>
   * 传递的参数实际上是 {@link com.github.anilople.javalua.instruction.Instruction.Opcode.OpMode#iABC} 模式指令里的
   * {@link com.github.anilople.javalua.instruction.Instruction.Opcode.OpArgMask#OpArgK} 类型，总共 9 bits
   * <p>
   * 如果最高位是1，那么参数里存放的是常量表索引，把最高位去掉就可以得到索引值。 如果最高位是0，参数里存放的就是寄存器索引值
   */
  void getRK(int rk);
}
