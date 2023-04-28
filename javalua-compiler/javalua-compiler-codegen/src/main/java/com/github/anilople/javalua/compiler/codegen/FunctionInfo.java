package com.github.anilople.javalua.compiler.codegen;

import com.github.anilople.javalua.compiler.ast.exp.FunctionDefExp;

/**
 * 函数原型
 *
 * @author wxq
 */
public interface FunctionInfo {

  static FunctionInfo newFunctionInfo(FunctionInfo parent, FunctionDefExp functionDefExp) {
    //    return new FunctionInfoImpl(
    //        parent,
    //        functionDefExp.isVararg(),
    //        functionDefExp.getNumParams()
    //    );
    return null;
  }

  FunctionInfo getSubFunctionInfo(int index);

  int indexOfConstant(Object constant);

  /**
   * 分配1个寄存器
   *
   * @return 寄存器的索引
   */
  int allocRegister();

  /** 回收最近分配的寄存器 */
  void freeRegister();

  /**
   * 分配连续的n个寄存器
   *
   * @return 第1个寄存器的索引
   */
  int allocRegisters(int n);

  /** 回收最近分配的n个寄存器 */
  void freeRegisters(int n);

  /** 进入新的作用域 */
  void enterScope();

  /** 进入循环块(for repeat while)的作用域 */
  void enterLoopScope();

  /** 退出作用域，在 {@link #enterScope()} 之后使用 */
  void exitScope();

  /**
   * page 331.
   *
   * <p>将处于开启状态的Upvalue闭合，如果有需要处理的局部变量，该方法将会产生1条JMP指令， 其操作数A给出需要处理的第一个局部变量的寄存器索引
   */
  void closeOpenUpvalues();

  /**
   * 在当前作用域里添加1个局部变量
   *
   * @param name 局部变量的名字
   * @return 分配的寄存器索引
   */
  int addLocalVariable(String name);

  boolean existLocalVariable(String name);

  /**
   * page 322 slotOfLocVar
   *
   * <p>获取局部变量对应的寄存器索引
   *
   * @return 寄存器索引
   * @throws java.util.NoSuchElementException 如果变量不存在
   */
  int getIndexOfLocalVariable(String name);

  /**
   * page 322 removeLocVar
   *
   * <p>解绑作用域内的局部变量
   *
   * <p>同一作用域内可能有多个同名变量，都需要解除绑定
   *
   * <p>如果上层作用域有同名变量，还需要重新绑定上层的同名变量
   *
   * @param name 局部变量名
   */
  void removeLocalVariable(String name);

  boolean existsUpvalue(String name);

  int bindUpvalue(String name);

  /** page 325 */
  int indexOfUpvalue(String name);

  /** @return 已经生成的最后一条指令的程序计数器（Program Counter） */
  int pc();

  /** page 326 */
  void fixSBx(int pc, int sBx);
}
