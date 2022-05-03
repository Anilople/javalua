package com.github.anilople.javalua.api.stdlib;

import com.github.anilople.javalua.state.LuaBoolean;
import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaNumber;
import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaTable;
import com.github.anilople.javalua.state.LuaValue;
import java.io.PrintStream;

/**
 * page 179
 *
 * 第一个Java函数
 *
 * 对应Lua里的print函数
 *
 * 每个元素之间用 '\t' 分隔，最后会追加 '\n'
 *
 * @author wxq
 */
public class print extends AbstractJavaFunction {

  private static final print INSTANCE = new print(System.out);

  public static print getInstance() {
    return INSTANCE;
  }

  private final PrintStream printStream;

  public print(PrintStream printStream) {
    this.printStream = printStream;
  }

  static String toJavaString(LuaValue luaValue) {
    if (null == luaValue) {
      throw new IllegalStateException("lua value is java's null");
    }
    if (LuaValue.NIL.equals(luaValue)) {
      return luaValue.toString();
    }
    if (luaValue instanceof LuaString) {
      LuaString luaString = (LuaString) luaValue;
      return luaString.toString();
    }
    if (luaValue instanceof LuaInteger) {
      LuaInteger luaInteger = (LuaInteger) luaValue;
      return luaInteger.toString();
    }
    if (luaValue instanceof LuaNumber) {

    }
    if (luaValue instanceof LuaBoolean) {
      LuaBoolean luaBoolean = (LuaBoolean) luaValue;
      return luaBoolean.toString();
    }
    if (luaValue instanceof LuaTable) {
      String hashCode = String.format("%016x", luaValue.hashCode());
      return "table: " + hashCode;
    }
    throw new UnsupportedOperationException("cannot print " + luaValue.type() + " " + luaValue);
  }

  @Override
  public Integer apply(LuaState luaState) {
    int nArgs = luaState.getTop();
    for (int index = 1; index <= nArgs; index++) {
      LuaValue luaValue = luaState.toLuaValue(index);
      String s = toJavaString(luaValue);
      printStream.print(s);
      if (index < nArgs) {
        printStream.print("\t");
      }
    }
    printStream.print("\n");
    return 0;
  }
}
