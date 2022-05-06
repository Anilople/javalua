package com.github.anilople.javalua.state;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.constant.LuaConstants;
import com.github.anilople.javalua.instruction.Instruction;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author wxq
 */
public class CallFrameImpl extends LuaStackImpl implements CallFrame {
  private final CallFrame prev;
  private final LuaClosure luaClosure;
  private final LuaValue[] varargs;
  int pc;
  private final Instruction[] instructions;

  /**
   * page 192
   *
   * Upvalue有开放（Open）状态 和 闭合（Closed）状态。
   *
   * 这里暂时记录所有还处于开放状态的Upvalue
   *
   * key是局部变量的寄存器索引，value是Upvalue指针
   */
  Map<Integer, LuaUpvalue> openuvs = new HashMap<>();

  public CallFrameImpl() {
    throw new UnsupportedOperationException();
  }

  public CallFrameImpl(
      int stackSize,
      int registerCount,
      CallFrame prev,
      LuaClosure luaClosure,
      LuaValue[] args,
      LuaValue[] varargs) {
    super(stackSize, registerCount);
    this.prev = prev;
    this.luaClosure = luaClosure;
    this.varargs = varargs;
    if (null != luaClosure && luaClosure.isPrototype()) {
      this.instructions = Instruction.convert(luaClosure.getPrototype().getCode().getCode());
    } else {
      this.instructions = null;
    }
    // 闭包要用的参数
    for (int i = 0; i < args.length; i++) {
      this.set(i + 1, args[i]);
    }
  }

  /**
   * @return 链表的长度
   */
  public int length() {
    int count = 0;
    for (CallFrame top = this; top != null; top = top.getPrevious()) {
      count++;
    }
    return count;
  }

  @Override
  public int pc() {
    return this.pc;
  }

  @Override
  public CallFrame getPrevious() {
    return this.prev;
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

  @Override
  public Instruction currentInstruction() {
    if (this.pc < this.instructions.length) {
      return this.instructions[this.pc];
    }
    return null;
  }

  @Override
  public LuaValue get(int index) {
    if (index == LuaConstants.LUA_REGISTRY_INDEX) {
      throw new IllegalArgumentException(
          "stack frame doesn't save registry, please get it in lua state");
    }
    if (index < LuaConstants.LUA_REGISTRY_INDEX) {
      // upvalue
      int upvalueIndex = LuaConstants.LUA_REGISTRY_INDEX - index - 1;
      return this.luaClosure.getLuaValue(upvalueIndex);
    }
    return super.get(index);
  }

  @Override
  public void set(int index, LuaValue luaValue) {
    if (index < LuaConstants.LUA_REGISTRY_INDEX) {
      // upvalue
      int upvalueIndex = LuaConstants.LUA_REGISTRY_INDEX - index - 1;
      this.luaClosure.changeLuaUpvalueReferencedLuaValue(upvalueIndex, luaValue);
      return;
    }
    super.set(index, luaValue);
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
        + "luaClosure.luaUpvalues:"
        + (null == this.luaClosure ? " lua closure is null " : this.luaClosure.toString())
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

  @Override
  public LuaValue[] getVarargs() {
    return this.varargs;
  }

  @Override
  public LuaClosure getLuaClosure() {
    return this.luaClosure;
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
  public LuaValue[] popNResults(int n) {
    LuaValue[] luaValues = new LuaValue[n];
    for (int i = 0; i < n; i++) {
      LuaValue luaValue = this.pop();
      luaValues[i] = luaValue;
    }
    return luaValues;
  }

  /**
   * 与{@link #popNResults(int)}不同，栈顶的元素在数组的最后一位
   */
  public LuaValue[] popNArgs(int n) {
    LuaValue[] luaValues = new LuaValue[n];
    for (int i = 0; i < n; i++) {
      LuaValue luaValue = this.pop();
      int index = n - i - 1;
      luaValues[index] = luaValue;
    }
    return luaValues;
  }

  public void pushN(LuaValue[] luaValues) {
    for (LuaValue luaValue : luaValues) {
      this.push(luaValue);
    }
  }

  public void pushN(LuaValue[] luaValues, int numberOfResultsWanted) {
    final int number = numberOfResultsWanted < 0 ? luaValues.length : numberOfResultsWanted;
    for (int i = 0; i < number; i++) {
      final LuaValue luaValue;
      if (i < luaValues.length) {
        luaValue = luaValues[i];
      } else {
        luaValue = LuaValue.NIL;
      }
      this.push(luaValue);
    }
  }

  public boolean containsOpenUpvalue(int index) {
    return this.openuvs.containsKey(index);
  }

  public LuaUpvalue getOpenUpvalue(int index) {
    return this.openuvs.get(index);
  }

  public void putOpenUpvalue(int index, LuaUpvalue luaUpvalue) {
    this.openuvs.put(index, luaUpvalue);
  }

  public void forEachOpenUpvalue(BiConsumer<Integer, LuaUpvalue> biConsumer) {
    this.openuvs.forEach(biConsumer);
  }

  public void removeOpenUpvalue(int index) {
    this.openuvs.remove(index);
  }
}
