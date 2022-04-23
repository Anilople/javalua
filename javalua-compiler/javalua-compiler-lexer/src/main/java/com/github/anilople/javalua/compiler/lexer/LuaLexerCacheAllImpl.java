package com.github.anilople.javalua.compiler.lexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 把所有的token一次性都读出来
 *
 * @author wxq
 */
public class LuaLexerCacheAllImpl implements LuaLexer {

  private final List<LuaToken> luaTokens;

  private final int size;

  private int index;

  public LuaLexerCacheAllImpl(LuaLexer luaLexer) {
    List<LuaToken> luaTokens = new ArrayList<>();
    while (luaLexer.hasNext()) {
      LuaToken luaToken = luaLexer.next();
      luaTokens.add(luaToken);
    }
    this.luaTokens = Collections.unmodifiableList(luaTokens);
    this.size = this.luaTokens.size();
  }

  boolean hasNext(int n) {
    return this.index + n <= this.size;
  }

  void ensureHasNext(int n) {
    if (!this.hasNext(n)) {
      throw new NoSuchElementException(n + " elements");
    }
  }

  @Override
  public LuaToken previewNext() {
    this.ensureHasNext(1);
    return this.luaTokens.get(this.index);
  }

  @Override
  public List<LuaToken> previewNext(int n) {
    return Collections.unmodifiableList(
        this.luaTokens.subList(
            this.index, this.index + n
        )
    );
  }

  @Override
  public boolean hasNext() {
    return this.hasNext(1);
  }

  @Override
  public LuaToken next() {
    LuaToken luaToken = this.previewNext();
    this.index++;
    return luaToken;
  }
}
