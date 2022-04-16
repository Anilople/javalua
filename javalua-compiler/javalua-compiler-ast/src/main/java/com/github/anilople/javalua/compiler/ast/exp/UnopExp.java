package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;
import com.github.anilople.javalua.compiler.ast.Unop;

/**
 * page 281
 *
 * unop exp
 *
 * @author wxq
 */
public class UnopExp extends AbstractExp {
  private final Unop unop;
  private final Exp exp;

  public UnopExp(Unop unop, Exp exp) {
    super(unop.getLuaAstLocation());
    this.unop = unop;
    this.exp = exp;
  }
}
