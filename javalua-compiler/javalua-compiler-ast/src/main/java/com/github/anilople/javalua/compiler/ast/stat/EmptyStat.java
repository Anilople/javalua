package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import java.io.PrintStream;

/**
 *  ‘;’
 *
 * @author wxq
 */
public class EmptyStat extends AbstractStat {
  public EmptyStat(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print(';');
  }
}
