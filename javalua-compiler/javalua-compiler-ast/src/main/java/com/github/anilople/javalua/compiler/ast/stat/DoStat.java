package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * do block end
 *
 * @author wxq
 */
public class DoStat extends AbstractStat {
  private final Block block;

  public DoStat(LuaAstLocation luaAstLocation, Block block) {
    super(luaAstLocation);
    this.block = block;
  }
}
