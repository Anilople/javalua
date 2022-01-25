package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.constant.DataTypeSizeConstants.Java;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.ByteArrayInputStream;

/**
 * @author wxq
 */
public class DecodeInputStream {

  private final ByteArrayInputStream inputStream;

  public DecodeInputStream(ByteArrayInputStream inputStream) {
    this.inputStream = inputStream;
  }

  public DecodeInputStream(byte[] bytes) {
    this.inputStream = new ByteArrayInputStream(bytes);
  }

  byte readByte() {
    return (byte) inputStream.read();
  }

  int readInt() {
    byte[] bytes = readNBytesWithException(Java.INT);
    return ByteUtils.decodeInt(bytes);
  }

  long readLong() {
    byte[] bytes = readNBytesWithException(Java.LONG);
    return ByteUtils.decodeLong(bytes);
  }

  double readDouble() {
    byte[] bytes = readNBytesWithException(Java.LONG);
    return ByteUtils.decodeDouble(bytes);
  }

  byte[] readNBytes(int length) {
    return readNBytesWithException(length);
  }

  private byte[] readNBytesWithException(int length) {
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++) {
      int value = inputStream.read();
      if (value < 0) {
        throw new IllegalStateException(
            "cannot read " + length + " bytes i = " + i + " input stream = " + inputStream);
      }
      bytes[i] = (byte) value;
    }
    return bytes;
  }
}
