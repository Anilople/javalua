package com.github.anilople.javalua.chunk;

import java.io.IOException;

/**
 * @author wxq
 */
public class LuaBoolean implements Encodable, Decodable {

  private static final byte[] TRUE = new byte[]{1};
  private static final byte[] FALSE = new byte[]{0};

  private boolean value;

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    var intValue = inputStream.readByte();
    if (intValue == 0) {
      this.value = false;
    } else if (intValue == 1) {
      this.value = true;
    } else {
      throw new IllegalStateException("the byte must be 0 or 1, but it is " + intValue);
    }
  }

  @Override
  public byte[] encode() {
    return value ? TRUE : FALSE;
  }
}
