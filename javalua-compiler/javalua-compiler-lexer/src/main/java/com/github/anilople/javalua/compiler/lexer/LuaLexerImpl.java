package com.github.anilople.javalua.compiler.lexer;

import com.github.anilople.javalua.util.CachedIterator;

/**
 * @author wxq
 */
class LuaLexerImpl implements LuaLexer {

  private final CachedIterator<LuaToken> cachedIterator;

  public LuaLexerImpl(String luaCode, String sourceCodeFileName) {
    LuaResource luaResource = new LuaResource(luaCode, sourceCodeFileName);
    LuaTokenIterator luaTokenIterator = new LuaTokenIterator(luaResource);
    this.cachedIterator = CachedIterator.newCachedIterator(luaTokenIterator);
  }

  public LuaLexerImpl(String luaCode) {
    this(luaCode, "unknown");
  }

  @Override
  public LuaToken previewNext() {
    return this.cachedIterator.previewNext();
  }

  @Override
  public boolean hasNext() {
    return this.cachedIterator.hasNext();
  }

  @Override
  public LuaToken next() {
    return this.cachedIterator.next();
  }
}
