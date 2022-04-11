package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * '...'
 *
 * @author wxq
 */
public class VarargExp extends AbstractExp {

  public VarargExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
