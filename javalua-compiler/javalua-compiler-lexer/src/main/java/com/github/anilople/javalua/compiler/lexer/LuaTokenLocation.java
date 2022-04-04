package com.github.anilople.javalua.compiler.lexer;

import lombok.Data;

/**
 * @author wxq
 */
@Data
public class LuaTokenLocation {
  private final String sourceCodeFileName;
  private final int lineNumber;
  private final int columnNumber;

  public LuaTokenLocation(String sourceCodeFileName, int lineNumber, int columnNumber) {
    this.sourceCodeFileName = sourceCodeFileName;
    this.lineNumber = lineNumber;
    this.columnNumber = columnNumber;
  }
}
