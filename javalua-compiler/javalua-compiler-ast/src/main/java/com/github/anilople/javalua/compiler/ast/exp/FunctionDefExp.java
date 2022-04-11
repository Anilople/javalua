package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * functiondef ::= function funcbody
 *
 * @author wxq
 */
public class FunctionDefExp extends AbstractExp {
//  private final FuncBo
  public FunctionDefExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
