package com.github.anilople.javalua.compiler.ast;

import lombok.Getter;

/**
 * 字符串字面量
 *
 * @author wxq
 */
@Getter
public class Name extends AbstractLuaAst {
  private final String identifier;

  public Name(LuaAstLocation luaAstLocation, String identifier) {
    super(luaAstLocation);
    this.identifier = identifier;
  }
}
