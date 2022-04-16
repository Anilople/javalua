package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * if exp then block {elseif exp then block} [else block] end
 *
 * @author wxq
 */
public class IfStat extends AbstractStat {
  private final Exp exp;
  private final Block block;
  /**
   * {elseif exp then block}
   */
  private final List<Entry<Exp, Block>> elseif;

  /**
   * [else block]
   */
  private final Optional<Block> elseBlock;

  public IfStat(LuaAstLocation luaAstLocation, Exp exp, Block block,
      List<Entry<Exp, Block>> elseif,
      Optional<Block> elseBlock) {
    super(luaAstLocation);
    this.exp = exp;
    this.block = block;
    this.elseif = elseif;
    this.elseBlock = elseBlock;
  }
}
