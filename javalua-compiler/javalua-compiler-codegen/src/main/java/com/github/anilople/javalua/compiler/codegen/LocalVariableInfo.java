package com.github.anilople.javalua.compiler.codegen;

/**
 * @author wxq
 */
public class LocalVariableInfo {

  /**
   * 局部变量名
   */
  private final String name;
  /**
   * 作用域的层次
   */
  private final int scopeLevel;
  /**
   * 与局部变量名绑定的寄存器索引
   */
  private final int slot;
  /**
   * 局部变量是否被闭包捕获
   */
  private final boolean captured;

  public LocalVariableInfo(String name, int scopeLevel, int slot, boolean captured) {
    this.name = name;
    this.scopeLevel = scopeLevel;
    this.slot = slot;
    this.captured = captured;
  }
}
