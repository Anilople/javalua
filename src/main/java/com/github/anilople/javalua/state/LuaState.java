package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.chunk.Prototype;
import com.github.anilople.javalua.instruction.operator.ArithmeticOperator;
import com.github.anilople.javalua.instruction.operator.BitwiseOperator;
import com.github.anilople.javalua.instruction.operator.ComparisonOperator;
import com.github.anilople.javalua.util.Return2;
import java.io.PrintStream;

public interface LuaState {

  static LuaState create() {
    return new DefaultLuaStateImpl(20, null);
  }

  static LuaState create(int stackSize, Prototype prototype) {
    return new DefaultLuaStateImpl(stackSize, prototype);
  }

  static void printStack(LuaState luaState) {
    printStack(luaState, System.out);
  }

  static void printStack(LuaState luaState, PrintStream printStream) {
    final var top = luaState.getTop();
    for (int i = 1; i <= top; i++) {
      final var luaType = luaState.luaType(i);
      switch (luaType) {
        case LUA_TBOOLEAN:
          LuaBoolean luaBoolean = luaState.toLuaBoolean(i);
          printStream.print(luaBoolean.toString());
          break;
        case LUA_TNUMBER:
          if (luaState.isLuaInteger(i)) {
            LuaInteger luaInteger = luaState.toLuaInteger(i);
            printStream.print(luaInteger.toString());
          } else if (luaState.isLuaNumber(i)) {
            LuaNumber luaNumber = luaState.toLuaNumber(i);
            printStream.print(luaNumber.toString());
          } else {
            throw new IllegalStateException("index " + i + " is not a number");
          }

          break;
        case LUA_TSTRING:
          LuaString luaString = luaState.toLuaString(i);
          printStream.print(luaString.toString());
          break;
        default:
          String typeName = luaState.luaTypeName(luaType);
          printStream.print("[" + typeName + "]");
          break;
      }
    }
    printStream.println();
  }

  /* basic stack manipulation */
  int getTop();

  int absIndex(int index);

  boolean checkStack(int n);

  void pop(int n);

  /**
   * page 60
   */
  void copy(int from, int to);

  void pushValue(int index);

  /**
   * {@code #define lua_replace(L,idx)	(lua_copy(L, -1, (idx)), lua_pop(L, 1))}
   *
   * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lua.h#L373">lua.h#L373 lua_replace</a>
   */
  void replace(int index);

  void insert(int index);

  void remove(int index);

  void rotate(int index, int n);

  void setTop(int index);

  /* access functions (stack -> Java) */
  String luaTypeName(LuaType type);

  LuaType luaType(int index);

  boolean isLuaNone(int index);

  boolean isLuaNil(int index);

  boolean isLuaNoneOrLuaNil(int index);

  boolean isLuaBoolean(int index);

  boolean isLuaInteger(int index);

  boolean isLuaNumber(int index);

  boolean isLuaString(int index);

  LuaBoolean toLuaBoolean(int index);

  LuaInteger toLuaInteger(int index);

  Return2<LuaInteger, Boolean> toLuaIntegerX(int index);

  /**
   * @return 返回 0 如果索引对应的值不是 number 类型
   */
  LuaNumber toLuaNumber(int index);

  Return2<LuaNumber, Boolean> toLuaNumberX(int index);

  /**
   * Java的{@link #toString()}有特殊含义，所以命名成 lua string
   *
   * 复用{@link #toLuaStringX(int)}
   */
  LuaString toLuaString(int index);
  /**
   * 会修改栈.
   */
  Return2<LuaString, Boolean> toLuaStringX(int index);

  /* push functions (Java -> stack) */
  void pushLuaNil();

  void pushLuaBoolean(LuaBoolean b);

  void pushLuaInteger(LuaInteger value);

  void pushLuaNumber(LuaNumber value);

  void pushLuaString(LuaString value);

  void arithmetic(ArithmeticOperator operator);

  void bitwise(BitwiseOperator operator);

  /**
   * 不改变栈的状态
   */
  LuaBoolean compare(int index1, int index2, ComparisonOperator operator);

  void len(int index);

  /**
   * 如果 n = 0，push 空字符；
   */
  void concat(int n);
}
