package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.util.ArrayUtils;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import lombok.Data;

/**
 * @author wxq
 */
@Data
public class LuaString implements Encodable, Decodable {

  public static final LuaString NULL = new LuaString();
  private static final byte[] ZERO = new byte[] {0};
  byte first;
  byte[] bytes;

  @Override
  public byte[] encode() {
    if (0 == first) {
      return ZERO;
    } else if ((~first) != 0) {
      // <= 0xFD
      // if first == 0xFF, ~first => 0x00
      return ArrayUtils.concat((byte) (first + 1), bytes);
    } else {
      // >= 0xFE
      byte[] secondPart = ByteUtils.encodeLong(bytes.length + 1);
      return ArrayUtils.concat(first, secondPart, bytes);
    }
  }

  @Override
  public void decode(DecodeInputStream inputStream) throws IOException {
    this.first = inputStream.readByte();
    if (0 == this.first) {
      // do nothing
    } else if ((~this.first) != 0) {
      // <= 0xFD
      // if first == 0xFF, ~first => 0x00
      int length = Byte.toUnsignedInt(this.first) - 1;
      bytes = inputStream.readNBytes(length);
    } else {
      // >= 0xFE
      long length = ByteUtils.decodeLong(inputStream.readNBytes(8)) - 1;
      bytes = inputStream.readNBytes((int) length);
    }
  }
}