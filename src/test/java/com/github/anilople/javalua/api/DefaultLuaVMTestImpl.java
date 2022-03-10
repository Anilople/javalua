package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.state.CallFrame;
import com.github.anilople.javalua.state.CallStack;
import com.github.anilople.javalua.state.LuaClosure;
import com.github.anilople.javalua.state.LuaValue;

/**
 * 给测试使用
 *
 * @author wxq
 */
public class DefaultLuaVMTestImpl extends DefaultLuaVMImpl {

  public DefaultLuaVMTestImpl(int stackSize, Prototype prototype) {
    super(stackSize, prototype);
  }

  public void pushCallFrameForPrototype(LuaClosure luaClosure, LuaValue[] allArgs) {
    this.callStack.pushCallFrameForPrototype(luaClosure, allArgs);
  }

  public CallFrame topCallFrame() {
    return this.callStack.topCallFrame();
  }
}