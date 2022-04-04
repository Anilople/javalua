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
  private final TokenLocation location;
  /**
   * token的类型
   */
  private final TokenEnums kind;
  /**
   * token的内容
   */
  private final String content;

  public LuaToken(TokenLocation location,
      TokenEnums kind, String content) {
    this.location = location;
    this.kind = kind;
    this.content = content;
  }

  public LuaToken(TokenLocation location, TokenEnums kind) {
    this(location, kind, kind.getContent());
  }

  /**
   * token在源码中的位置
   */
  public static class TokenLocation {
    private final LuaResource luaResource;
    public TokenLocation(LuaResource luaResource) {
      this.luaResource = luaResource;
    }

    @Override
    public String toString() {
      return "lua code file name '" + luaResource.getSourceCodeFileName() + "'"
          + " line " + this.luaResource.getCurrentLineNumber()
          + " column " + this.luaResource.getCurrentLineColumnOffset();
    }
  }
}
