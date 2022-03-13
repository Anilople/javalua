package com.github.anilople.javalua.instruction.operator;

import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;
import java.util.function.BiFunction;

/**
 * @author wxq
 */
abstract class AbstractOperator<T, U, R> implements Operator<T, U, R> {
  /**
   * enum的数值，对应官方C语言实现里的宏定义
   */
  private final int enumCount;
  /**
   * 代码中的表示
   */
  private final String content;
  /**
   * 运算符
   */
  private final BiFunction<T, U, R> function;

  /**
   * 元方法的名字
   */
  private final LuaString metaMethodName;

  protected AbstractOperator(int enumCount, String content, BiFunction<T, U, R> function,
      LuaString metaMethodName) {
    this.enumCount = enumCount;
    this.content = content;
    this.function = function;
    this.metaMethodName = metaMethodName;
  }

  @Override
  public BiFunction<T, U, R> getOperator() {
    return this.function;
  }

  public LuaString getMetaMethodName() {
    return this.metaMethodName;
  }
}
