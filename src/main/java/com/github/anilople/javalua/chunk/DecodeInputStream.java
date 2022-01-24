package com.github.anilople.javalua.chunk;

import com.github.anilople.javalua.constant.SizeConstants.Java;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;

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

  int readInt() throws IOException {
    byte[] bytes = inputStream.readNBytes(Java.INT);
    return ByteUtils.decodeInt(bytes);
  }

  long readLong() throws IOException {
    byte[] bytes = inputStream.readNBytes(Java.LONG);
    return ByteUtils.decodeLong(bytes);
  }

  double readDouble() throws IOException {
    byte[] bytes = inputStream.readNBytes(Java.LONG);
    return ByteUtils.decodeDouble(bytes);
  }

  byte[] readNBytes(int length) throws IOException {
    return inputStream.readNBytes(length);
  }
}
