package com.github.anilople.javalua.compiler.ast.stat;

import com.github.anilople.javalua.compiler.ast.AbstractLuaAst;
import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * @author wxq
 */
public class AbstractStat extends AbstractLuaAst implements Stat {

  public AbstractStat(LuaAstLocation luaAstLocation) {
    super(luaAstLocation);
  }
}
