package com.github.anilople.javalua.state;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.constant.LuaConstants;
import com.github.anilople.javalua.util.Return2;
import java.util.Arrays;

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

  CallStack() {}

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
   * @return 函数参数 和 varargs
   */
  static Return2<LuaValue[], LuaValue[]> resolveArgsAndVarargs(
      LuaValue[] allArgs, boolean isVararg, int nParams) {
    if (isVararg) {
      int nArgs = allArgs.length;
      if (nArgs > nParams) {
        // 传过来的参数个数大于 固定参数个数，那么多出来的参数，就放到 varargs 中
        // 前 nParams 个是固定参数，后面的是 vararg
        var args = Arrays.copyOfRange(allArgs, 0, nParams);
        var varargs = Arrays.copyOfRange(allArgs, nParams, allArgs.length);
        return new Return2<>(args, varargs);
      }
    }

    // 都是固定参数
    // https://www.lua.org/pil/5.html
    // 可以允许参数个数与函数签名内容不一致
    if (nParams < allArgs.length) {
      // 只使用前半部分
      LuaValue[] realArgs = Arrays.copyOfRange(allArgs, 0, nParams);
      return new Return2<>(realArgs, new LuaValue[0]);
    } else if (nParams == allArgs.length) {
      // 使用全部
      return new Return2<>(allArgs, new LuaValue[0]);
    } else {
      // 不够用，用 nil 来补充
      LuaValue[] realArgs = new LuaValue[nParams];
      System.arraycopy(allArgs, 0, realArgs, 0, allArgs.length);
      Arrays.fill(realArgs, allArgs.length, nParams, LuaValue.NIL);
      return new Return2<>(realArgs, new LuaValue[0]);
    }
  }

  /**
   * @param luaClosure 闭包
   * @param allArgs 传递给闭包的所有参数，闭包不一定能全部用上
   */
  public void pushCallFrameForPrototype(LuaClosure luaClosure, LuaValue[] allArgs) {
    final Prototype prototype = luaClosure.prototype;
    if (prototype == null) {
      throw new IllegalArgumentException("prototype cannot be null");
    }
    var nParams = prototype.getNumParams();
    var isVararg = 1 == prototype.getIsVararg();
    var argsAndVarargs = resolveArgsAndVarargs(allArgs, isVararg, nParams);
    final LuaValue[] args = argsAndVarargs.r0;
    final LuaValue[] varargs = argsAndVarargs.r1;

    this.callFrame =
        new CallFrame(
            this.callFrame,
            prototype.getRegisterCount() * 2 + LuaConstants.LUA_MIN_STACK,
            prototype.getRegisterCount(),
            luaClosure,
            args,
            varargs);
  }

  /**
   * TODO, 更多参数
   */
  void pushCallFrameForJavaFunction(LuaClosure luaClosure, LuaValue[] allArgs) {
    int nArgs = allArgs.length;
    this.callFrame =
        new CallFrame(
            this.callFrame,
            nArgs + LuaConstants.LUA_MIN_STACK,
            nArgs,
            luaClosure,
            allArgs,
            new LuaValue[0]);
  }

  public CallFrame topCallFrame() {
    return this.callFrame;
  }
}
