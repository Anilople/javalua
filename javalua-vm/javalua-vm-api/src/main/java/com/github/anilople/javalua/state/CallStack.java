package com.github.anilople.javalua.state;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.util.Return2;
import com.github.anilople.javalua.util.SpiUtils;
import java.util.Arrays;

/**
 * 函数调用栈
 *
 * @author wxq
 */
public interface CallStack {

  static CallStack of(int stackSize, Prototype prototype) {
    CallStack callStack = SpiUtils.loadOneInterfaceImpl(CallStack.class);
    callStack.init(stackSize, prototype);
    return callStack;
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

  void init(int stackSize, Prototype prototype);

  CallFrame popCallFrame();

  /**
   * @param luaClosure 闭包
   * @param allArgs 传递给闭包的所有参数，闭包不一定能全部用上
   */
  void pushCallFrameForPrototype(LuaClosure luaClosure, LuaValue[] allArgs);

  void pushCallFrameForJavaFunction(LuaClosure luaClosure, LuaValue[] allArgs);

  CallFrame topCallFrame();
}
