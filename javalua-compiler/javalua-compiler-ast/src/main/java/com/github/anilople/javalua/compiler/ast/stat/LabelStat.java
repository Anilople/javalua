package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Name;
import lombok.Getter;

/**
 * label ::= ‘::’ Name ‘::’
 *
 * @author wxq
 */
@Getter
public class LabelStat extends AbstractStat {
  private final Name name;

  public LabelStat(Name name) {
    super(name.getLocation());
    this.name = name;
  }
}
