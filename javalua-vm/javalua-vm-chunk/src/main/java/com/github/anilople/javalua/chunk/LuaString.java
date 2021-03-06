package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.constant.DataTypeSizeConstants.Java;
import com.github.anilople.javalua.io.Decodable;
import com.github.anilople.javalua.io.DecodeInputStream;
import com.github.anilople.javalua.io.Encodable;
import com.github.anilople.javalua.util.ArrayUtils;
import com.github.anilople.javalua.util.ByteUtils;
import java.util.Arrays;
import java.util.Objects;
import lombok.Data;

/**
 * @author wxq
 */
@Data
class LuaString implements Encodable, Decodable {
  private static final byte[] ZERO = new byte[] {0};
  public static final LuaString NULL = new LuaString();
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
  public void decode(DecodeInputStream inputStream) {
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

  String toJavaString() {
    return new String(this.bytes);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LuaString luaString = (LuaString) o;
    return first == luaString.first && Arrays.equals(bytes, luaString.bytes);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(first);
    result = 31 * result + Arrays.hashCode(bytes);
    return result;
  }

  @Override
  public String toString() {
    return "LuaString{" + "first=" + first + ", bytes=" + Arrays.toString(bytes) + '}';
  }
}
