package com.github.anilople.javalua.compiler.ast;

import lombok.Data;

/**
 * ast在源码中的位置
 *
 * @author wxq
 */
@Data
public class LuaAstLocation {
  private final int line;
  private final int column;
}
