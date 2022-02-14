package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import java.io.IOException;
import lombok.Data;

/**
 * @author wxq
 */
@Data
class LuaBoolean implements Encodable, Decodable {

  private static final byte[] TRUE = new byte[] {1};
  private static final byte[] FALSE = new byte[] {0};

  boolean value;

  @Override
  public void decode(DecodeInputStream inputStream) {
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
