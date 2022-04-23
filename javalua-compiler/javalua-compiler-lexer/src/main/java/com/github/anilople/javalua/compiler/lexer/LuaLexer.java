package com.github.anilople.javalua.compiler.lexer;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_EOF;

import com.github.anilople.javalua.compiler.lexer.enums.TokenEnums;
import com.github.anilople.javalua.util.CachedIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

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
    return this.lookAheadTest(tokenEnums -> tokenEnums.equals(kind));
  }

  /**
   * 检测接下来的token类型是否匹配，不会跳过这个token
   * @param tokenKindPredicate token类型的判定函数
   * @return false 当没有token了，或者判定不通过
   */
  default boolean lookAheadTest(Predicate<TokenEnums> tokenKindPredicate) {
    if (!this.hasNext()) {
      return false;
    }
    LuaToken token = this.lookAhead();
    return tokenKindPredicate.test(token.getKind());
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
    while (luaLexer.hasNext()) {
      LuaToken luaToken = luaLexer.next();
      if (TOKEN_EOF.equals(luaToken.getKind())) {
        break;
      }
      luaTokens.add(luaToken);
    }
    return luaTokens;
  }

  static List<LuaToken> lexer(String luaCode) {
    return lexer(luaCode, "unknown");
  }

  static LuaLexer newLuaLexer(String luaCode) {
    LuaLexer luaLexer = new LuaLexerImpl(luaCode);
    return new LuaLexerCacheAllImpl(luaLexer);
  }

  static LuaLexer newLuaLexer(String luaCode, String sourceCodeFilePath) {
    LuaLexer luaLexer = new LuaLexerImpl(luaCode, sourceCodeFilePath);
    return new LuaLexerCacheAllImpl(luaLexer);
  }
}
