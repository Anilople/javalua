package com.github.anilople.javalua.compiler.ast.exp;

import com.github.anilople.javalua.compiler.ast.LuaAstLocation;

/**
 * @author wxq
 */
public class LiteralStringExp extends AbstractExp {
  private final String content;

  public LiteralStringExp(LuaAstLocation luaAstLocation, String content) {
    super(luaAstLocation);
    this.content = content;
  }
}
