package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.AbstractLuaAst;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * @author wxq
 */
abstract class AbstractExp extends AbstractLuaAst {

  public AbstractExp(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
