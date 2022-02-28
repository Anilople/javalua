package com.github.anilople.javalua.state;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 调用帧
 *
 * @author wxq
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CallFrame extends LuaStackImpl implements LuaStack {
  final CallFrame prev;
  final LuaClosure luaClosure;
  final LuaValue[] varargs;
  int pc;
  private final Instruction[] instructions;

  public CallFrame(
      CallFrame prev, int stackSize, int registerCount,
      LuaClosure luaClosure, LuaValue[] args, LuaValue[] varargs) {
    super(stackSize, registerCount);
    this.prev = prev;
    this.luaClosure = luaClosure;
    this.varargs = varargs;
    if (luaClosure.getPrototype() != null) {
      this.instructions = luaClosure.getPrototype().getCode().getInstructions();
    } else {
      this.instructions = null;
    }
    // 闭包要用的参数
    for (int i = 0; i < args.length; i++) {
      this.set(i + 1, args[i]);
    }
  }

  public CallFrame(int size, Prototype prototype) {
    super(size, prototype.getRegisterCount());
    this.prev = null;
    this.luaClosure = new LuaClosure(prototype);
    this.varargs = null;
    this.instructions = prototype.getCode().getInstructions();
  }

  /**
   * @return 链表的长度
   */
  public int length() {
    int count = 0;
    for (var top = this; top != null; top = top.prev) {
      count++;
    }
    return count;
  }

  static String toString(LuaValue[] luaValues, int length) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      LuaValue luaValue = luaValues[i];
      if (luaValue instanceof LuaClosure) {
        stringBuilder.append("[").append(luaValue.getClass().getSimpleName()).append("]");
      } else {
        stringBuilder.append("[").append(luaValue).append("]");
      }
    }
    return stringBuilder.toString();
  }

  Instruction currentInstruction() {
    if (this.pc < this.instructions.length) {
      return this.instructions[this.pc];
    }
    return null;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + "[\n"
        + "pc:"
        + this.pc
        + "\n"
        + "current instruction:"
        + this.currentInstruction()
        + "\n"
        + "stack:"
        + toString(this.getLuaValues(), this.getTop())
        + "\n"
        + "]";
  }

  /**
   * 获取当前函数对应的 {@link Prototype}
   */
  public Prototype getPrototype() {
    return this.luaClosure.getPrototype();
  }

  /**
   * 获取 索引 处的 {@link Prototype}
   * @param index 索引
   */
  public Prototype getPrototype(int index) {
    Prototype current = this.getPrototype();
    return current.getProtos()[index];
  }

  /**
   * @return null 如果没有指令了
   */
  public Instruction fetch() {
    if (this.pc < this.instructions.length) {
      var instruction = this.instructions[this.pc];
      this.addPC(1);
      return instruction;
    } else {
      // 没有指令了
      return null;
    }
  }

  public void addPC(int delta) {
    this.pc += delta;
  }

  /**
   * pop出n个{@link LuaValue}，栈顶的在数组的最前面
   */
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
    var nRegs = this.getPrototype().getRegisterCount();
    var numOfReturnArgs = this.getTop() - nRegs;
    return this.popN(numOfReturnArgs);
  }

  public void pushN(LuaValue[] luaValues) {
    for (LuaValue luaValue : luaValues) {
      this.push(luaValue);
    }
  }

  public void pushN(LuaValue[] luaValues, int nResults) {
    for (int i = 0; i < nResults; i++) {
      final LuaValue luaValue;
      if (i < luaValues.length) {
        luaValue = luaValues[i];
      } else {
        luaValue = LuaValue.NIL;
      }
      this.push(luaValue);
    }
  }
}
