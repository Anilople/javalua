package com.github.anilople.javalua.chunk;

import java.io.IOException;

/**
 * @author wxq
 */
class LuaStringNull extends LuaString {
  private static final byte[] ZERO = new byte[] {0};
  static final LuaStringNull NULL = new LuaStringNull();

  private LuaStringNull() {}

  @Override
  public byte[] encode() {
    return ZERO;
  }

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    throw new UnsupportedOperationException();
  }
}
