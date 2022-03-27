package com.github.anilople.javalua.util;

/**
 * @author wxq
 * @param <R0> 第一个返回值的类型
 * @param <R1> 第二个返回值的类型
 * @param <R2> 第三个返回值的类型
 */
public class Return3<R0, R1, R2> {
  public final R0 r0;
  public final R1 r1;
  public final R2 r2;

  public Return3(R0 r0, R1 r1, R2 r2) {
    this.r0 = r0;
    this.r1 = r1;
    this.r2 = r2;
  }
}
