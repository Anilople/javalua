package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.exp.Exp;

/**
 * repeat block until exp
 *
 * @author wxq
 */
public class RepeatStat extends AbstractStat {
  private final Block block;
  private final Exp exp;

  public RepeatStat(LuaAstLocation luaAstLocation,
      Block block, Exp exp) {
    super(luaAstLocation);
    this.block = block;
    this.exp = exp;
  }
}
