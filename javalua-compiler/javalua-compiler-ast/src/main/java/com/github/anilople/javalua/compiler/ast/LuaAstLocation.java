package com.github.anilople.javalua.compiler.ast;

import lombok.Data;

/**
 * ast在源码中的位置
 *
 * @author wxq
 */
@Data
public class LuaAstLocation {
  public static final LuaAstLocation EMPTY = new LuaAstLocation(1, 0);
  private final int line;
  private final int column;

  @Override
  public String toString() {
    return "{" + "line=" + line + ", column=" + column + '}';
  }
}
