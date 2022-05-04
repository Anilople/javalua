package com.github.anilople.javalua.state;

import com.github.anilople.javalua.util.SpiUtils;

/**
 * 栈索引从1开始
 *
 * 在 [1, top] 内的是有效索引
 *
 * 在 [1, size] 内的是可接受索引
 *
 * @author wxq
 */
public interface LuaStack {

  static LuaStack of(int size) {
    return of(size, 0);
  }

  static LuaStack of(int size, int registerCount) {
    return SpiUtils.loadOneInterfaceImpl(
        LuaStack.class,
        int.class, int.class,
        size, registerCount
    );
  }

  int size();

  /**
   * 检查栈的空闲空间是否还可以容纳至少n个值，
   *
   * 如果不满足，则扩容
   */
  void check(int n);

  void push(LuaValue luaValue);

  LuaValue pop();

  /**
   * 转为绝对值索引，并不考虑索引是否有效
   */
  int absIndex(int index);

  /**
   * page 56
   *
   * @return {@link LuaValue#NIL} 如果索引无效
   */
  LuaValue get(int index);

  /**
   * page 56
   * @throws IllegalArgumentException 如果索引无效
   */
  void set(int index, LuaValue luaValue);

  int getTop();

  void reverse(int from, int to);
}
