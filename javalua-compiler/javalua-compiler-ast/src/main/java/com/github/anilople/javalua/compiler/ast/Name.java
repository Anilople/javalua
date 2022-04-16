package com.github.anilople.javalua.compiler.ast;

/**
 * 字符串字面量
 *
 * @author wxq
 */
public class Name extends AbstractLuaAst {
  private final String name;

  public Name(LuaAstLocation luaAstLocation, String name) {
    super(luaAstLocation);
    this.name = name;
  }
}
