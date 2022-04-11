package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import lombok.Data;

/**
 * repeat block until exp
 *
 * @author wxq
 */
@Data
public class RepeatStat implements Stat {
  private final Block block;
  private final Exp exp;
}
