package com.github.anilople.javalua.state;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.constant.LuaConstants;
import com.github.anilople.javalua.util.ArrayUtils;
import com.github.anilople.javalua.util.Return2;

/**
 * 函数调用栈
 *
 * @author wxq
 */
public class CallStack {

  static CallStack of(int stackSize, Prototype prototype) {
    CallStack callStack = new CallStack();
    callStack.callFrame = new CallFrame(stackSize, prototype);
    return callStack;
  }

  private CallFrame callFrame = null;

  private CallStack() {}

  CallFrame popCallFrame() {
    if (null == callFrame) {
      throw new IllegalStateException("there is no call frame");
    }
    CallFrame popped = this.callFrame;
    this.callFrame = this.callFrame.prev;
    return popped;
  }
  /**
   * 计算需要传递给函数的 固定参数 和 vararg
   *
   * @param allArgs 所有参数
   * @param isVararg 函数的参数中是否存在vararg
   * @param nParams 函数的固定参数个数
   */
  static Return2<LuaValue[], LuaValue[]> resolveArgsAndVarargs(
      LuaValue[] allArgs, boolean isVararg, int nParams) {
    if (isVararg) {
      int nArgs = allArgs.length;
      if (nArgs > nParams) {
        // 传过来的参数个数大于 固定参数个数，那么多出来的参数，就放到 varargs 中
        // 前 nParams 个是固定参数，后面的是 vararg
        var args = ArrayUtils.slice(allArgs, 0, nParams);
        var varargs = ArrayUtils.slice(allArgs, nParams);
        return new Return2<>(args, varargs);
      }
    }

    // 都是固定参数
    if (allArgs.length != nParams) {
      throw new IllegalArgumentException(
          "所有参数的个数是" + allArgs.length + " 和函数所需的固定参数个数" + nParams + "不同");
    }
    return new Return2<>(allArgs, new LuaValue[0]);
  }

  /**
   * @param luaClosure 闭包
   * @param allArgs 传递给闭包的所有参数，闭包不一定能全部用上
   */
  void pushCallFrameForPrototype(LuaClosure luaClosure, LuaValue[] allArgs) {
    final Prototype prototype = luaClosure.prototype;
    if (prototype == null) {
      throw new IllegalArgumentException("prototype cannot be null");
    }
    var nParams = prototype.getNumParams();
    var isVararg = 1 == prototype.getIsVararg();
    var argsAndVarargs = resolveArgsAndVarargs(allArgs, isVararg, nParams);
    final LuaValue[] args = argsAndVarargs.r0;
    final LuaValue[] varargs = argsAndVarargs.r1;

    this.callFrame = new CallFrame(
        this.callFrame, prototype.getRegisterCount() * 2 + LuaConstants.LUA_MIN_STACK, prototype.getRegisterCount(),
        luaClosure, args, varargs);
  }

  /**
   * TODO, 更多参数
   */
  void pushCallFrameForJavaFunction(LuaClosure luaClosure, LuaValue[] allArgs) {
    int nArgs = allArgs.length;
    this.callFrame = new CallFrame(this.callFrame, nArgs + LuaConstants.LUA_MIN_STACK, nArgs,
        luaClosure, allArgs, new LuaValue[0]);
  }

  public CallFrame topCallFrame() {
    return this.callFrame;
  }
}
