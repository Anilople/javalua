package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.exp.PrefixExp;

/**
 * functioncall ::=  prefixexp args | prefixexp ‘:’ Name args
 *
 * @author wxq
 */
public abstract class FunctionCall extends AbstractStat {

  private final PrefixExp prefixExp;

  public FunctionCall(PrefixExp prefixExp) {
    super(prefixExp.getLuaAstLocation());
    this.prefixExp = prefixExp;
  }
}
