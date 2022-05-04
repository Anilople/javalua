package com.github.anilople.javalua.state;

import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.Instruction;
import com.github.anilople.javalua.util.SpiUtils;
import java.util.function.BiConsumer;

/**
 * 调用帧
 *
 * @author wxq
 */
public interface CallFrame extends LuaStack {

  static CallFrame newCallFrame(
      int stackSize,
      int registerCount,
      CallFrame prev,
      LuaClosure luaClosure,
      LuaValue[] args,
      LuaValue[] varargs) {
    return SpiUtils.loadOneInterfaceImpl(
        CallFrame.class,
        new Class[] {
          int.class,
          int.class,
          CallFrame.class,
          LuaClosure.class,
          LuaValue[].class,
          LuaValue[].class,
        },
        new Object[] {stackSize, registerCount, prev, luaClosure, args, varargs});
  }

  static CallFrame newCallFrame(int size, Prototype prototype) {
    return CallFrame.newCallFrame(
        size,
        prototype.getRegisterCount(),
        null,
        LuaClosure.newPrototypeLuaClosure(prototype),
        new LuaValue[] {},
        null);
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

  /**
   * @return 链表的长度
   */
  int length();

  int pc();

  /**
   * 获取上一个节点.
   */
  CallFrame getPrevious();

  Instruction currentInstruction();

  /**
   * 获取当前函数对应的 {@link Prototype}
   */
  Prototype getPrototype();

  /**
   * 获取 索引 处的 {@link Prototype}
   * @param index 索引
   */
  Prototype getPrototype(int index);

  LuaValue[] getVarargs();

  LuaClosure getLuaClosure();

  /**
   * @return null 如果没有指令了
   */
  Instruction fetch();

  void addPC(int delta);

  /**
   * pop出n个{@link LuaValue}，栈顶的在数组的最前面
   */
  default LuaValue[] popNResults(int n) {
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
  default LuaValue[] popNArgs(int n) {
    LuaValue[] luaValues = new LuaValue[n];
    for (int i = 0; i < n; i++) {
      LuaValue luaValue = this.pop();
      int index = n - i - 1;
      luaValues[index] = luaValue;
    }
    return luaValues;
  }

  default void pushN(LuaValue[] luaValues) {
    for (LuaValue luaValue : luaValues) {
      this.push(luaValue);
    }
  }

  default void pushN(LuaValue[] luaValues, int numberOfResultsWanted) {
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

  boolean containsOpenUpvalue(int index);

  LuaUpvalue getOpenUpvalue(int index);

  void putOpenUpvalue(int index, LuaUpvalue luaUpvalue);

  void forEachOpenUpvalue(BiConsumer<Integer, LuaUpvalue> biConsumer);

  void removeOpenUpvalue(int index);
}
