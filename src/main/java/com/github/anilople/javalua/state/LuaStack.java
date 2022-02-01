package com.github.anilople.javalua.state;

import java.util.ArrayList;
import java.util.List;

/**
 * 栈索引从1开始
 *
 * @author wxq
 */
public class LuaStack {
  private LuaValue[] luaValues;
  private int top;

  public LuaStack(int size) {
    this.luaValues = new LuaValue[size];
    this.top = 0;
  }

  /**
   * 检查栈的空闲空间是否还可以容纳至少n个值，
   *
   * 如果不满足，则扩容
   */
  public void check(int n) {
    int free = this.luaValues.length - this.top;
    if (free <= 0) {
      // 扩容
      LuaValue[] newLuaValues = new LuaValue[(this.luaValues.length + free) * 2];
      System.arraycopy(this.luaValues, 0, newLuaValues, 0, this.luaValues.length);
      this.luaValues = newLuaValues;
    }
  }

  public void push(LuaValue luaValue) {
    if (this.luaValues.length == this.top) {
      throw new IllegalStateException("stack overflow in lua stack");
    }
    this.luaValues[this.top] = luaValue;
    this.top++;
  }

  public LuaValue pop() {
    if (this.top < 1) {
      throw new IllegalStateException("stack overflow when pop");
    }
    this.top--;
    LuaValue luaValue = this.luaValues[this.top];
    this.luaValues[this.top] = LuaValue.NIL;
    return luaValue;
  }

  /**
   * 转为绝对值索引，并不考虑索引是否有效
   */
  public int absIndex(int index) {
    if (index >= 0) {
      return index;
    }
    return index + this.top + 1;
  }

  void ensureValid(int index) {
    var absIndex = this.absIndex(index);
    if (absIndex <= 0) {
      throw new IllegalArgumentException("invalid absIndex " + absIndex + " <= 0, index " + index);
    }
    if (absIndex > this.top) {
      throw new IllegalArgumentException("invalid absIndex " + absIndex + " > top " + this.top + ", index " + index);
    }
  }

  public boolean isValid(int index) {
    var absIndex = this.absIndex(index);
    return absIndex > 0 && absIndex <= this.top;
  }

  /**
   * @return null 如果索引无效
   */
  public LuaValue get(int index) {
    if (!this.isValid(index)) {
      return null;
    }
    var absIndex = this.absIndex(index);
    return this.luaValues[absIndex - 1];
  }

  /**
   * @throws IllegalArgumentException 如果索引无效
   */
  public void set(int index, LuaValue luaValue) {
    this.ensureValid(index);
    var absIndex = this.absIndex(index);
    this.luaValues[absIndex - 1] = luaValue;
  }

  public int getTop() {
    return this.top;
  }

  public void reverse(int from, int to) {
    from = absIndex(from);
    to = absIndex(to);
    while (from < to) {
      var fromValue = this.get(from);
      var toValue = this.get(to);
      this.set(from, toValue);
      this.set(to, fromValue);
      from++;
      to--;
    }
  }
}
