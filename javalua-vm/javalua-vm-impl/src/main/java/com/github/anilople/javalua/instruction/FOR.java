package com.github.anilople.javalua.instruction;

/**
 * 数值for循环：{@link FORPREP}和{@link FORLOOP}
 *
 * 通用for循环：{@link TFORCALL}和{@link TFORLOOP}
 *
 * @author wxq
 */
abstract class FOR extends AbstractInstruction {

  public FOR(int originCodeValue) {
    super(originCodeValue);
  }
}
