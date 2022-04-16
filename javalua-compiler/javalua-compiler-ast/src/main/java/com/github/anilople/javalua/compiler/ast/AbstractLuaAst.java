package com.github.anilople.javalua.compiler.ast;

import lombok.Data;

/**
 * @author wxq
 */
@Data
public abstract class AbstractLuaAst implements LuaAst {
  private final LuaAstLocation luaAstLocation;
}
