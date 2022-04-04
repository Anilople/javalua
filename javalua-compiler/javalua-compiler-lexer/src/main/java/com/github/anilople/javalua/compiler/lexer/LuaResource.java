package com.github.anilople.javalua.compiler.lexer;

import com.github.anilople.javalua.io.TextResource;
import lombok.Getter;

/**
 * lua源代码
 *
 * @author wxq
 */
@Getter
class LuaResource extends TextResource {

  /**
   * 源代码文件名
   */
  private final String sourceCodeFileName;

  public LuaResource(String sourceCodeContent, String sourceCodeFileName) {
    super(sourceCodeContent);
    this.sourceCodeFileName = sourceCodeFileName;
  }

  public LuaResource(String sourceCodeContent) {
    this(sourceCodeContent, "unknown");
  }
}
