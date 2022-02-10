package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.constant.DataTypeSizeConstants.Java;
import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import com.github.anilople.javalua.util.ArrayUtils;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.Data;

/**
 * @author wxq
 */
@Data
class LuaString implements Encodable, Decodable {
  public static final LuaString NULL = LuaStringNull.NULL;
  private static final byte[] ZERO = new byte[] {0};
  byte first;
  byte[] bytes = new byte[0];

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
      long length = ByteUtils.decodeLong(inputStream.readNBytes(Java.LONG)) - 1;
      bytes = inputStream.readNBytes((int) length);
    }
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.getClass());
    stringBuilder.append("[");
    stringBuilder.append("first byte = ").append(this.first);
    stringBuilder.append(" length = ").append(this.bytes.length);
    stringBuilder.append(" content = ").append(new String(this.bytes, StandardCharsets.UTF_8));
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}
