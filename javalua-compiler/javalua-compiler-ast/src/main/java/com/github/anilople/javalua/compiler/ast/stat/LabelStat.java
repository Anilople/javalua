package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Name;

/**
 * label ::= ‘::’ Name ‘::’
 *
 * @author wxq
 */
public class LabelStat extends AbstractStat {
  private final Name name;

  public LabelStat(Name name) {
    super(name.getLocation());
    this.name = name;
  }
}
