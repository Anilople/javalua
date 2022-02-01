package com.github.anilople.javalua.state;

import com.github.anilople.javalua.api.LuaType;
import com.github.anilople.javalua.util.Return2;
import java.io.PrintStream;

public interface LuaState {

  static LuaState create() {
    return new DefaultLuaStateImpl();
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
          LuaNumber luaNumber = luaState.toLuaNumber(i);
          printStream.print(luaNumber.toString());
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

  void copy(int from, int to);

  void pushValue(int index);

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
}