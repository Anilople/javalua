package com.github.anilople.javalua.api;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.chunk.Upvalue;
import com.github.anilople.javalua.config.Config;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.state.DefaultLuaStateImpl;
import com.github.anilople.javalua.state.LuaClosure;
import com.github.anilople.javalua.state.LuaUpvalue;
import com.github.anilople.javalua.state.LuaValue;
import java.util.ArrayList;
import java.util.List;

/**
 * page 93
 *
 * @author wxq
 */
class DefaultLuaVMImpl extends DefaultLuaStateImpl implements LuaVM {

  DefaultLuaVMImpl(int stackSize, Prototype prototype) {
    super(stackSize, prototype);
  }

  @Override
  public int pc() {
    return this.callStack.topCallFrame().getPc();
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
    var luaValue = LuaValue.of(constant);
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
        nextCallFrame = nextCallFrame.getPrev()) {
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
    var prototype = this.callStack.topCallFrame().getLuaClosure().getPrototype();
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

  LuaUpvalue[] resolveLuaUpvalues(Prototype prototype) {
    Upvalue[] upvalues = prototype.getUpvalues();
    final int len = upvalues.length;
    LuaUpvalue[] luaUpvalues = new LuaUpvalue[len];
    for (int i = 0; i < len; i++) {
      Upvalue upvalue = upvalues[i];
      int upvalueIndex = (int) upvalue.getIdx();
      switch (upvalue.getInstack()) {
        case 1:
          // 这个upvalue捕获的是当前函数的局部变量
          if (this.callStack.topCallFrame().containsOpenUpvalue(upvalueIndex)) {
            LuaUpvalue luaUpvalue = this.callStack.topCallFrame().getOpenUpvalue(upvalueIndex);
            luaUpvalues[i] = luaUpvalue;
          } else {
            // Open 状态的 upvalue 不存在，从栈帧中获取
            LuaValue luaValue = this.callStack.topCallFrame().get(upvalueIndex);
            LuaUpvalue luaUpvalue = new LuaUpvalue(luaValue);
            luaUpvalues[i] = luaUpvalue;
            // 并且 将其在当前栈帧中，变成 Open 状态
            this.callStack.topCallFrame().putOpenUpvalue(upvalueIndex, luaUpvalue);
          }
          break;
        case 0:
          // 这个upvalue捕获的是父函数中的 upvalue
          LuaUpvalue luaUpvalue = this.callStack.topCallFrame().getLuaClosure().getLuaUpvalue(upvalueIndex);
          luaUpvalues[i] = luaUpvalue;
          break;
        default:
          throw new IllegalStateException("upvalue in stack cannot be " + upvalue.getInstack());
      }
    }
    return luaUpvalues;
  }

  @Override
  public void loadPrototype(int index) {
    final var frame = this.callStack.topCallFrame();
    var prototype = frame.getPrototype(index);
    LuaClosure luaClosure = new LuaClosure(prototype);
    frame.push(luaClosure);

    // page 192
    // 需要根据函数原型里的Upvalue表来初始化闭包的Upvalue值

    final Upvalue[] upvalues = prototype.getUpvalues();
    final int len = upvalues.length;
    for (int i = 0; i < len; i++) {
      Upvalue upvalue = upvalues[i];
      final int upvalueIndex = (int) upvalue.getIdx();
      final LuaUpvalue luaUpvalue;
      switch (upvalue.getInstack()) {
        case 1:
          // 这个upvalue捕获的是当前函数的局部变量
          if (frame.containsOpenUpvalue(upvalueIndex)) {
            luaUpvalue = frame.getOpenUpvalue(upvalueIndex);
          } else {
            // Open 状态的 upvalue 不存在，从栈帧中获取
            luaUpvalue = new LuaUpvalue(frame.get(upvalueIndex));
            // 并且 将其在当前栈帧中，变成 Open 状态
            frame.putOpenUpvalue(upvalueIndex, luaUpvalue);
          }
          break;
        case 0:
          // 这个upvalue捕获的是父函数中的 upvalue
          luaUpvalue = frame.getLuaClosure().getLuaUpvalue(upvalueIndex);
          break;
        default:
          throw new IllegalStateException("upvalue in stack cannot be " + upvalue.getInstack());
      }
      luaClosure.setLuaUpvalue(i, luaUpvalue);
    }
  }

  @Override
  public void closeUpvalues(int a) {
    List<Integer> openUpvaluesForRemove = new ArrayList<>();

    this.callStack.topCallFrame().forEachOpenUpvalue(((index, luaUpvalue) -> {
      if (index >= a - 1) {
        openUpvaluesForRemove.add(index);
      }
    }));

    for (Integer index : openUpvaluesForRemove) {
      this.callStack.topCallFrame().removeOpenUpvalue(index);
    }
  }
}
