package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.chunk.Upvalue;
import com.github.anilople.javalua.config.Config;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.state.CallFrame;
import com.github.anilople.javalua.state.LuaClosure;
import com.github.anilople.javalua.state.LuaStateImpl;
import com.github.anilople.javalua.state.LuaUpvalue;
import com.github.anilople.javalua.state.LuaValue;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * page 93
 *
 * @author wxq
 */
public class LuaVMImpl extends LuaStateImpl implements LuaVM {

  public LuaVMImpl() {}

  @Override
  public void init(int stackSize, Prototype prototype) {
    super.init(stackSize, prototype);
  }

  @Override
  public int pc() {
    return this.callStack.topCallFrame().pc();
  }

  @Override
  public void addPC(int n) {
    this.callStack.topCallFrame().addPC(n);
  }

  @Override
  public Instruction fetch() {
    return this.callStack.topCallFrame().fetch();
  }

  @Override
  public void getConst(int index) {
    var prototype = this.callStack.topCallFrame().getPrototype();
    var constants = prototype.getConstants();
    var constant = constants.getConstant(index);
    final LuaValue luaValue;
    if (constant.isLuaBoolean()) {
      luaValue = LuaValue.of(constant.getLuaBooleanInJava());
    } else if (constant.isLuaInteger()) {
      luaValue = LuaValue.of(constant.getLuaIntegerInJava());
    } else if (constant.isLuaNumber()) {
      luaValue = LuaValue.of(constant.getLuaNumberInJava());
    } else if (constant.isLuaString()) {
      luaValue = LuaValue.of(constant.getLuaStringInJava());
    } else {
      luaValue = LuaValue.NIL;
    }
    this.callStack.topCallFrame().push(luaValue);
  }

  @Override
  public void getRK(int rk) {
    if (rk > 0xFF) {
      // 常量表索引
      this.getConst(rk & 0xFF);
    } else {
      // 寄存器索引
      this.pushValue(rk + 1);
    }
  }

  void print(Instruction instruction) {
    System.out.println("run " + instruction);
    System.out.println("pc:" + this.pc());

    var topCallFrame = this.callStack.topCallFrame();
    System.out.println("call frame:" + topCallFrame.length());
    for (var nextCallFrame = topCallFrame;
        nextCallFrame != null;
        nextCallFrame = nextCallFrame.getPrevious()) {
      System.out.println(nextCallFrame);
    }
    System.out.println();
    System.out.println();
  }

  @Override
  protected void runLuaClosure() {
    for (var instruction = this.fetch(); null != instruction; instruction = this.fetch()) {
      instruction.applyTo(this);
      if (Config.getInstance().needPrint()) {
        this.print(instruction);
      }
    }
  }

  @Override
  public int getRegisterCount() {
    var prototype = this.callStack.topCallFrame().getPrototype();
    return prototype.getRegisterCount();
  }

  @Override
  public void loadVararg(int requiredResults) {
    var callFrame = this.callStack.topCallFrame();
    LuaValue[] varargs = callFrame.getVarargs();
    if (requiredResults < 0) {
      // all
      for (LuaValue luaValue : varargs) {
        this.callStack.topCallFrame().push(luaValue);
      }
    } else {
      for (int offset = 0; offset < requiredResults; offset++) {
        LuaValue luaValue = varargs[offset];
        this.callStack.topCallFrame().push(luaValue);
      }
    }
  }

  static LuaUpvalue resolveLuaUpvalue(CallFrame callFrame, Upvalue upvalue) {
    // page 192
    // 需要根据函数原型里的Upvalue表来初始化闭包的Upvalue值

    final int upvalueIndex = (int) upvalue.getIdx();
    final LuaUpvalue luaUpvalue;
    switch (upvalue.getInstack()) {
      case 1:
        // 这个upvalue捕获的是当前函数的局部变量
        if (callFrame.containsOpenUpvalue(upvalueIndex)) {
          luaUpvalue = callFrame.getOpenUpvalue(upvalueIndex);
        } else {
          // Open 状态的 upvalue 不存在，从栈帧中获取
          final int index = upvalueIndex + 1;
          Supplier<LuaValue> getter = () -> callFrame.get(index);
          Consumer<LuaValue> setter = luaValue -> callFrame.set(index, luaValue);
          luaUpvalue = LuaUpvalue.newLuaUpvalue(getter, setter);
          // 并且 将其在当前栈帧中，变成 Open 状态
          callFrame.putOpenUpvalue(upvalueIndex, luaUpvalue);
        }
        break;
      case 0:
        // 这个upvalue捕获的是父函数中的 upvalue
        luaUpvalue = callFrame.getLuaClosure().getLuaUpvalue(upvalueIndex);
        break;
      default:
        throw new IllegalStateException("upvalue in stack cannot be " + upvalue.getInstack());
    }
    return luaUpvalue;
  }

  @Override
  public void loadPrototype(int index) {
    final var callFrame = this.callStack.topCallFrame();
    var prototype = callFrame.getPrototype(index);
    LuaClosure luaClosure = new LuaClosure(prototype);
    callFrame.push(luaClosure);

    // page 192
    // 需要根据函数原型里的Upvalue表来初始化闭包的Upvalue值

    final Upvalue[] upvalues = prototype.getUpvalues();
    final int len = upvalues.length;
    for (int i = 0; i < len; i++) {
      Upvalue upvalue = upvalues[i];
      LuaUpvalue luaUpvalue = resolveLuaUpvalue(callFrame, upvalue);
      luaClosure.setLuaUpvalue(i, luaUpvalue);
    }
  }

  @Override
  public void closeUpvalues(int a) {
    List<Integer> openUpvaluesForRemove = new ArrayList<>();

    this.callStack
        .topCallFrame()
        .forEachOpenUpvalue(
            ((index, luaUpvalue) -> {
              if (index >= a - 1) {
                openUpvaluesForRemove.add(index);
              }
            }));

    for (Integer index : openUpvaluesForRemove) {
      this.callStack.topCallFrame().removeOpenUpvalue(index);
    }
  }
}
