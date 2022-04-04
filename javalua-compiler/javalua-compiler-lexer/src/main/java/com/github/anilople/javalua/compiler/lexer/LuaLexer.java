package com.github.anilople.javalua.compiler.lexer;

import com.github.anilople.javalua.util.CachedIterator;
import java.util.ArrayList;
import java.util.List;

import static com.github.anilople.javalua.compiler.lexer.enums.TokenEnums.TOKEN_EOF;

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

  static List<LuaToken> lexer(String luaCode) {
    LuaTokenIterator luaLexer = new LuaTokenIterator(luaCode);
    List<LuaToken> luaTokens = new ArrayList<>();
    for (
        LuaToken luaToken = luaLexer.next();
        !TOKEN_EOF.equals(luaToken.getKind());
        luaToken = luaLexer.next()
    ) {
      luaTokens.add(luaToken);
    }
    return luaTokens;
  }

  static CachedIterator<LuaToken> newLuaLexer(String luaCode) {
    LuaTokenIterator iterator = new LuaTokenIterator(luaCode);
    return CachedIterator.newCachedIterator(iterator);
  }
}
