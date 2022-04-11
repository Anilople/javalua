package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.AbstractLuaAst;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * functioncall ::=  prefixexp args | prefixexp ‘:’ Name args
 *
 * @author wxq
 */
abstract class FunctionCall extends AbstractLuaAst implements Stat {

  public FunctionCall(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
