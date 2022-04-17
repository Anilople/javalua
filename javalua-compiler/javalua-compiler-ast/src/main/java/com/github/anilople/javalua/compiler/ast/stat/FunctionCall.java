package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;
import lombok.Getter;

/**
 * functioncall ::=  prefixexp args | prefixexp ‘:’ Name args
 *
 * @author wxq
 */
@Getter
public abstract class FunctionCall extends AbstractStat {

  private final PrefixExp prefixExp;

  public FunctionCall(PrefixExp prefixExp) {
    super(prefixExp.getLocation());
    this.prefixExp = prefixExp;
  }
}
