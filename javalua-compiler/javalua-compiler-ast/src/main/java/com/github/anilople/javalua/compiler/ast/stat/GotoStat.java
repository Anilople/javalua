package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Name;

/**
 * goto Name
 *
 * @author wxq
 */
public class GotoStat extends AbstractStat {
  private final Name name;

  public GotoStat(Name name) {
    super(name.getLuaAstLocation());
    this.name = name;
  }
}
