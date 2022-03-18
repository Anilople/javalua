package com.github.anilople.javalua.api.stdlib;

import com.github.anilople.javalua.api.LuaVM;
import com.github.anilople.javalua.state.JavaFunction;
import com.github.anilople.javalua.state.LuaInteger;
import com.github.anilople.javalua.state.LuaState;
import com.github.anilople.javalua.state.LuaString;
import com.github.anilople.javalua.state.LuaValue;
import java.io.PrintStream;

/**
 * page 179
 *
 * 第一个Java函数
 *
 * 对应Lua里的print函数
 *
 * @author wxq
 */
public class print implements JavaFunction {

  private final PrintStream printStream;

  private print(PrintStream printStream) {
    this.printStream = printStream;
  }

  public static void registerTo(LuaVM luaVM, PrintStream printStream) {
    print print = new print(printStream);
    luaVM.register(LuaValue.of("print"), print);
  }

  @Override
  public Integer apply(LuaState luaState) {
    int nArgs = luaState.getTop();
    for (int index = 1; index <= nArgs; index++) {
      LuaValue luaValue = luaState.toLuaValue(index);
      if (luaValue instanceof LuaString) {
        LuaString luaString = (LuaString) luaValue;
        printStream.print(luaString.getValue());
      } else if (luaValue instanceof LuaInteger) {
        LuaInteger luaInteger = (LuaInteger) luaValue;
        printStream.print(luaInteger.getValue());
      } else {
        throw new UnsupportedOperationException("cannot print " + luaValue);
      }
      if (index < nArgs) {
        printStream.print("\t");
      }
    }
    return 0;
  }
}
