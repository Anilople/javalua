package com.github.anilople.javalua.io;

import com.github.anilople.javalua.constant.DataTypeSizeConstants.Java;
import com.github.anilople.javalua.util.ArrayUtils;
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

  public byte readByte() {
    int value = inputStream.read();
    if (value < 0) {
      throw new IllegalStateException("have been exhaust");
    }
    return (byte) value;
  }

  public int readInt() {
    byte[] bytes = readNBytesWithException(Java.INT);
    return ByteUtils.decodeInt(bytes);
  }

  public long readLong() {
    byte[] bytes = readNBytesWithException(Java.LONG);
    return ByteUtils.decodeLong(bytes);
  }

  public double readDouble() {
    byte[] bytes = readNBytesWithException(Java.LONG);
    return ByteUtils.decodeDouble(bytes);
  }

  public byte[] readNBytes(int length) {
    return readNBytesWithException(length);
  }

  public int[] readNIntegers(int length) {
    byte[] bytes = readNBytes(length * Java.INT);
    return ArrayUtils.toIntArray(bytes);
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
