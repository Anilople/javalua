package com.github.anilople.javalua.compiler.ast.stat;

import lombok.Data;

/**
 * @author wxq
 */
@Data
public class BreakStat implements Stat {

  private final int line;

  public BreakStat(int line) {
    this.line = line;
  }
}
