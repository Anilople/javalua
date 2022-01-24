package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.util.ByteUtils;
import java.io.ByteArrayOutputStream;

/**
 * @author wxq
 */
class EncodeOutputStream {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

  void writeByte(byte value) {
    outputStream.write(value);
  }

  void writeShort(short value) {
    byte[] bytes = ByteUtils.encodeShort(value);
    outputStream.writeBytes(bytes);
  }

  void writeInt(int value) {
    byte[] bytes = ByteUtils.encodeInt(value);
    outputStream.writeBytes(bytes);
  }

  void writeLong(long value) {
    byte[] bytes = ByteUtils.encodeLong(value);
    outputStream.writeBytes(bytes);
  }

  void writeBytes(byte[] bytes) {
    outputStream.writeBytes(bytes);
  }

  byte[] toByteArray() {
    return outputStream.toByteArray();
  }
}
