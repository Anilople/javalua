package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Block;
import lombok.Data;

/**
 * do block end
 *
 * @author wxq
 */
@Data
public class DoStat implements Stat {
  private final Block block;
}
