package com.github.anilople.javalua.compiler.lexer;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_EOF;

import com.github.anilople.javalua.compiler.lexer.enums.TokenEnums;
import com.github.anilople.javalua.util.CachedIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

  /**
   * 检测接下来的token类型是否匹配，不会跳过这个token
   * @param kind token类型
   * @return false 当没有token了，或者token类型不匹配
   */
  default boolean lookAheadTest(TokenEnums kind) {
    if (!this.hasNext()) {
      return false;
    }
    LuaToken token = this.lookAhead();
    return token.getKind().equals(kind);
  }

  /**
   * 跳过1个符合所输类型的token
   *
   * @param kind token的类型
   * @return 被跳过的token
   * @throws NoSuchElementException 如果没有元素可以跳过
   * @throws IllegalStateException 如果token类型不符合
   */
  default LuaToken skip(TokenEnums kind) {
    if (!this.hasNext()) {
      throw new NoSuchElementException("no element to skip");
    }
    LuaToken luaToken = this.previewNext();
    if (!luaToken.getKind().equals(kind)) {
      throw new IllegalStateException(
          "token's kind not match, "
              + luaToken.getLocation()
              + " want to skip "
              + kind
              + " but get "
              + luaToken.getKind()
              + " in lexer");
    }
    // real skip it
    return this.next();
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
