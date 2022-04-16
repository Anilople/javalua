package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.exp.Exp;

/**
 * while exp do block end
 *
 * @author wxq
 */
public class WhileStat extends AbstractStat {
  private final Exp exp;
  private final Block block;

  public WhileStat(LuaAstLocation luaAstLocation, Exp exp, Block block) {
    super(luaAstLocation);
    this.exp = exp;
    this.block = block;
  }
}
