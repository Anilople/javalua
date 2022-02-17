package com.github.anilople.javalua.state;

import com.github.anilople.javalua.util.ArrayUtils;

/**
 * 函数调用栈
 *
 * @author wxq
 */
class CallStack {

  static CallStack of(int stackSize) {
    CallStack callStack = new CallStack();
    callStack.callFrame = new CallFrame(stackSize);
    return callStack;
  }

  private CallFrame callFrame = null;

  private CallStack() {}

  CallFrame popCallFrame() {
    if (null == callFrame) {
      throw new IllegalStateException("there is no call frame");
    }
    CallFrame popped = callFrame;
    CallFrame prev = popped.prev;
    this.callFrame = prev;
    return popped;
  }

  void pushCallFrame(LuaClosure luaClosure, int nArgs, LuaValue[] args) {
    var nRegs = luaClosure.prototype.getMaxStackSize();
    var nParams = luaClosure.prototype.getNumParams();
    var isVararg = 1 == luaClosure.prototype.getIsVararg();

    CallFrame newCallFrame = new CallFrame(nRegs + 20);

    newCallFrame.luaClosure = luaClosure;
    if (isVararg) {
      if (nArgs > nParams) {
        // 传过来的参数个数大于 固定参数个数，那么多出来的参数，就放到 varargs 中
        newCallFrame.varargs = ArrayUtils.slice(args, nParams);
      }
    }
    this.pushCallFrame(newCallFrame);
  }

  void pushCallFrame(CallFrame newCallFrame) {
    newCallFrame.prev = this.callFrame;
    this.callFrame = newCallFrame;
  }

  CallFrame topCallFrame() {
    return this.callFrame;
  }
}
