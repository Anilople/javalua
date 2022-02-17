package com.github.anilople.javalua.instruction;

/**
 * 和函数调用有关的指令
 *
 * @author wxq
 */
abstract class FunctionInstruction extends AbstractInstruction {

  public FunctionInstruction(int originCodeValue) {
    super(originCodeValue);
  }
}
