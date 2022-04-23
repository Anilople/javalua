package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.AbstractLuaAst;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * @author wxq
 */
public abstract class AbstractStat extends AbstractLuaAst implements Stat {

  public AbstractStat(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
