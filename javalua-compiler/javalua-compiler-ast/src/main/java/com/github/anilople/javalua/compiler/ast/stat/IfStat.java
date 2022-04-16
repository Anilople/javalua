package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.Data;

/**
 * if exp then block {elseif exp then block} [else block] end
 *
 * @author wxq
 */
@Data
public class IfStat implements Stat {
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
}
