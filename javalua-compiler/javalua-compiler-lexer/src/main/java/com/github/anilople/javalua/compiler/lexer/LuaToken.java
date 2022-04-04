package com.github.anilople.javalua.compiler.lexer;

import com.github.anilople.javalua.compiler.lexer.enums.TokenEnums;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class LuaToken {

  /**
   * token在源码中的位置
   */
  private final LuaTokenLocation location;
  /**
   * token的类型
   */
  private final TokenEnums kind;
  /**
   * token的内容
   */
  private final String content;

  public LuaToken(LuaTokenLocation location, TokenEnums kind, String content) {
    this.location = location;
    this.kind = kind;
    this.content = content;
  }

  public LuaToken(LuaTokenLocation location, TokenEnums kind) {
    this(location, kind, kind.getContent());
  }
}
