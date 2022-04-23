package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import java.io.PrintStream;

/**
 * break
 *
 * @author wxq
 */
public class BreakStat extends AbstractStat {

  public BreakStat(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print("break");
  }
}
