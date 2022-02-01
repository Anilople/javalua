package com.github.anilople.javalua.util;

/**
 * 2个返回值.
 *
 * @param <R0> 第一个返回值的类型
 * @param <R1> 第二个返回值的类型
 */
public class Return2<R0, R1> {
  public final R0 r0;
  public final R1 r1;

  public Return2(R0 r0, R1 r1) {
    this.r0 = r0;
    this.r1 = r1;
  }
}
