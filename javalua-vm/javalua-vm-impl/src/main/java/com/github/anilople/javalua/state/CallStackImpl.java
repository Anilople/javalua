package com.github.anilople.javalua.state;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.constant.LuaConstants;

/**
 * @author wxq
 */
public class CallStackImpl implements CallStack {

  private CallFrame callFrame;

  public CallStackImpl() {
    throw new UnsupportedOperationException();
  }

  public CallStackImpl(int stackSize, Prototype prototype) {
    this.callFrame = CallFrame.newCallFrame(stackSize, prototype);
  }

  @Override
  public CallFrame popCallFrame() {
    if (null == callFrame) {
      throw new IllegalStateException("there is no call frame");
    }
    CallFrame popped = this.callFrame;
    this.callFrame = this.callFrame.getPrevious();
    return popped;
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
    var argsAndVarargs = CallStack.resolveArgsAndVarargs(allArgs, isVararg, nParams);
    final LuaValue[] args = argsAndVarargs.r0;
    final LuaValue[] varargs = argsAndVarargs.r1;

    this.callFrame =
        CallFrame.newCallFrame(
            prototype.getRegisterCount() * 2 + LuaConstants.LUA_MIN_STACK,
            prototype.getRegisterCount(),
            this.callFrame,
            luaClosure,
            args,
            varargs);
  }

  /**
   * TODO, 更多参数
   */
  @Override
  public void pushCallFrameForJavaFunction(LuaClosure luaClosure, LuaValue[] allArgs) {
    int nArgs = allArgs.length;
    this.callFrame =
        CallFrame.newCallFrame(
            nArgs + LuaConstants.LUA_MIN_STACK,
            nArgs,
            this.callFrame,
            luaClosure,
            allArgs,
            new LuaValue[0]);
  }

  public CallFrame topCallFrame() {
    return this.callFrame;
  }
}
