package com.github.anilople.javalua.state;

/**
 * 调用帧
 *
 * @author wxq
 */
class CallFrame extends LuaStackImpl implements LuaStack {
  CallFrame prev;
  LuaClosure luaClosure;
  LuaValue[] varargs;
  int pc;

  public CallFrame(int size) {
    super(size);
  }

  public LuaValue[] popN(int n) {
    LuaValue[] luaValues = new LuaValue[n];
    for (int i = 0; i < n; i++) {
      LuaValue luaValue = this.pop();
      luaValues[i] = luaValue;
    }
    return luaValues;
  }

  /**
   * @return 函数的返回值
   */
  public LuaValue[] popResults() {
    var nRegs = luaClosure.prototype.getMaxStackSize();
    var numOfReturnArgs = this.getTop() - nRegs;
    return this.popN(numOfReturnArgs);
  }

  public void pushN(LuaValue[] luaValues) {
    for (LuaValue luaValue : luaValues) {
      this.push(luaValue);
    }
  }
}
