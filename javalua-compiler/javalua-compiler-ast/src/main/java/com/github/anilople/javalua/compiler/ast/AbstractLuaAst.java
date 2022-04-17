package com.github.anilople.javalua.compiler.ast;

/**
 * @author wxq
 */
public abstract class AbstractLuaAst implements LuaAst {
  private final LuaAstLocation location;

  protected AbstractLuaAst(LuaAstLocation location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "{" +
        "location=" + location +
        '}';
  }

  @Override
  public LuaAstLocation getLocation() {
    return location;
  }
}
