package com.github.anilople.javalua.state;


/**
 * 栈索引从1开始
 *
 * 在 [1, top] 内的是有效索引
 *
 * 在 [1, size] 内的是可接受索引
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

  int size() {
    return this.luaValues.length;
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

  /**
   * @throws IllegalArgumentException 如果不是 可接受索引
   */
  void ensureAcceptable(int index) {
    var absIndex = this.absIndex(index);
    if (absIndex <= 0) {
      throw new IllegalArgumentException("不是 可接受索引 absIndex " + absIndex + " <= 0, index " + index);
    }
    if (absIndex > this.size()) {
      throw new IllegalArgumentException(
          "不是 可接受索引 absIndex " + absIndex + " > size " + this.size() + ", index " + index);
    }
  }

  /**
   * page 56
   *
   * @return {@link LuaValue#NIL} 如果索引无效
   */
  public LuaValue get(int index) {
    var absIndex = this.absIndex(index);
    if (absIndex > 0 && absIndex <= this.top) {
      return this.luaValues[absIndex - 1];
    }
    return LuaValue.NIL;
  }

  /**
   * page 56
   * @throws IllegalArgumentException 如果索引无效
   */
  public void set(int index, LuaValue luaValue) {
    var absIndex = this.absIndex(index);
    if (absIndex > 0 && absIndex <= this.getTop()) {
      this.luaValues[absIndex - 1] = luaValue;
    } else {
      throw new IllegalArgumentException(
          "索引不是 有效索引 index " + index + " absIndex " + absIndex + " top " + this.getTop());
    }
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
