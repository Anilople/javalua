package com.github.anilople.javalua.compiler.ast;

import java.util.Optional;
import lombok.Getter;

/**
 * retstat ::= return [explist] [‘;’]
 *
 * @author wxq
 */
@Getter
public class Retstat extends AbstractLuaAst {
  private final Optional<ExpList> optionalExpList;

  public Retstat(LuaAstLocation luaAstLocation, Optional<ExpList> optionalExpList) {
    super(luaAstLocation);
    this.optionalExpList = optionalExpList;
  }
}
