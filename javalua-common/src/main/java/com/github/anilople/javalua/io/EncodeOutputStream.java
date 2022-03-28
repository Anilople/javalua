package com.github.anilople.javalua.io;

import com.github.anilople.javalua.util.ArrayUtils;
import com.github.anilople.javalua.util.ByteUtils;
import java.io.ByteArrayOutputStream;

/**
 * @author wxq
 */
public class EncodeOutputStream {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

  public void writeByte(byte value) {
    outputStream.write(value);
  }

  public void writeShort(short value) {
    byte[] bytes = ByteUtils.encodeShort(value);
    outputStream.writeBytes(bytes);
  }

  public void writeInt(int value) {
    byte[] bytes = ByteUtils.encodeInt(value);
    outputStream.writeBytes(bytes);
  }

  public void writeLong(long value) {
    byte[] bytes = ByteUtils.encodeLong(value);
    outputStream.writeBytes(bytes);
  }

  public void writeBytes(byte[] bytes) {
    outputStream.writeBytes(bytes);
  }

  public void writeIntegers(int[] values) {
    byte[] bytes = ArrayUtils.toByteArray(values);
    writeBytes(bytes);
  }

  public byte[] toByteArray() {
    return outputStream.toByteArray();
  }
}
