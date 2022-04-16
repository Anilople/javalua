package com.github.anilople.javalua.compiler.ast;

import java.util.Optional;

/**
 * funcbody ::= ‘(’ [parlist] ‘)’ block end
 *
 * @author wxq
 */
public class FuncBody extends AbstractLuaAst {
  private final Optional<ParList> optionalParList;
  private final Block block;

  public FuncBody(LuaAstLocation luaAstLocation, Optional<ParList> optionalParList, Block block) {
    super(luaAstLocation);
    this.optionalParList = optionalParList;
    this.block = block;
  }
}
