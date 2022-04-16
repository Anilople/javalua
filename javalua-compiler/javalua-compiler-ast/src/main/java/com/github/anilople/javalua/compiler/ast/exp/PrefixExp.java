package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * prefixexp ::= var | functioncall | ‘(’ exp ‘)’
 *
 * @author wxq
 */
public class PrefixExp extends AbstractExp {

  public PrefixExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
