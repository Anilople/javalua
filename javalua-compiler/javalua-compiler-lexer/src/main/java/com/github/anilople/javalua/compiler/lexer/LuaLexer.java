package com.github.anilople.javalua.compiler.lexer;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_EOF;

import com.github.anilople.javalua.util.CachedIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxq
 */
public interface LuaLexer extends CachedIterator<LuaToken> {

  /**
   * page 264
   *
   * 查看下一个token是什么，不会跳过这个token
   */
  default LuaToken lookAhead() {
    return this.previewNext();
  }

  static List<LuaToken> lexer(String luaCode, String sourceCodeFilePath) {
    LuaLexer luaLexer = newLuaLexer(luaCode, sourceCodeFilePath);
    List<LuaToken> luaTokens = new ArrayList<>();
    for (LuaToken luaToken = luaLexer.next();
        !TOKEN_EOF.equals(luaToken.getKind());
        luaToken = luaLexer.next()) {
      luaTokens.add(luaToken);
    }
    return luaTokens;
  }

  static List<LuaToken> lexer(String luaCode) {
    return lexer(luaCode, "unknown");
  }

  static LuaLexer newLuaLexer(String luaCode) {
    return new LuaLexerImpl(luaCode);
  }

  static LuaLexer newLuaLexer(String luaCode, String sourceCodeFilePath) {
    return new LuaLexerImpl(luaCode, sourceCodeFilePath);
  }
}
