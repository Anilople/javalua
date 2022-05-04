package com.github.anilople.javalua.instruction;

/**
 * 操作数的类型
 *
 * @author wxq
 */
public enum OpArgMask {
  /**
   * 这个操作数不会被使用
   */
  OpArgN,
  /**
   * iABC模式下表示寄存器索引
   * <p/>
   * iAsBx模式下表示跳转偏移
   */
  OpArgR,
  /**
   * 操作数可能表示布尔值，整数值，upvalue索引，子函数索引等
   */
  OpArgU,
  /**
   * 表示常量索引或者寄存器索引
   */
  OpArgK,
  ;
}
