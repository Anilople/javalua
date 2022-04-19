package com.github.anilople.javalua.compiler.lexer;

import com.github.anilople.javalua.util.CachedIterator;
import java.util.List;

/**
 * @author wxq
 */
class LuaLexerImpl implements LuaLexer {

  private final CachedIterator<LuaToken> cachedIterator;

  public LuaLexerImpl(String luaCode, String sourceCodeFilePath) {
    LuaResource luaResource = new LuaResource(luaCode, sourceCodeFilePath);
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
  public List<LuaToken> previewNext(int n) {
    return this.cachedIterator.previewNext(n);
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
