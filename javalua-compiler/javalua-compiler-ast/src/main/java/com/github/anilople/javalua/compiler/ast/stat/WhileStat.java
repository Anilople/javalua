package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import com.github.anilople.javalua.compiler.ast.exp.Exp;
import lombok.Data;

/**
 * while exp do block end
 *
 * @author wxq
 */
@Data
public class WhileStat implements Stat {
  private final Exp exp;
  private final Block block;
}
