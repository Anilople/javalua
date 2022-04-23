package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import java.io.PrintStream;

/**
 * repeat block until exp
 *
 * @author wxq
 */
public class RepeatStat extends AbstractStat {
  private final Block block;
  private final Exp exp;

  public RepeatStat(LuaAstLocation luaAstLocation, Block block, Exp exp) {
    super(luaAstLocation);
    this.block = block;
    this.exp = exp;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print("repeat");
    this.toLuaCodeIndent(printStream, this.block::toLuaCode);
    printStream.print("until ");
    this.exp.toLuaCode(printStream);
  }
}
