package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import java.io.PrintStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.Getter;

/**
 * if exp then block {elseif exp then block} [else block] end
 *
 * @author wxq
 */
@Getter
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

  public IfStat(
      LuaAstLocation luaAstLocation,
      Exp exp,
      Block block,
      List<Entry<Exp, Block>> elseif,
      Optional<Block> elseBlock) {
    super(luaAstLocation);
    this.exp = exp;
    this.block = block;
    this.elseif = elseif;
    this.elseBlock = elseBlock;
  }

  @Override
  public void toLuaCode(PrintStream printStream) {
    printStream.print("if ");
    this.exp.toLuaCode(printStream);
    printStream.print(" then");
    this.toLuaCodeIndent(printStream, this.block::toLuaCode);
    for (Entry<Exp, Block> entry : this.elseif) {
      Exp exp = entry.getKey();
      Block block = entry.getValue();
      printStream.print("elseif ");
      exp.toLuaCode(printStream);
      printStream.print(" then");
      this.toLuaCodeIndent(printStream, block::toLuaCode);
    }
    if (this.elseBlock.isPresent()) {
      printStream.print("else");
      this.toLuaCodeIndent(printStream, this.elseBlock.get()::toLuaCode);
    }
    printStream.print("end");
  }
}
