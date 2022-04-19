package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.Name;
import lombok.Getter;

/**
 * goto Name
 *
 * @author wxq
 */
@Getter
public class GotoStat extends AbstractStat {
  private final Name name;

  public GotoStat(Name name) {
    super(name.getLocation());
    this.name = name;
  }
}
