package com.github.anilople.javalua.compiler.ast;

import java.util.Optional;

/**
 * retstat ::= return [explist] [‘;’]
 *
 * @author wxq
 */
public class Retstat extends AbstractLuaAst {
  private final Optional<ExpList> optionalExpList;

  public Retstat(LuaAstLocation luaAstLocation, Optional<ExpList> optionalExpList) {
    super(luaAstLocation);
    this.optionalExpList = optionalExpList;
  }
}
