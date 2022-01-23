package com.github.anilople.javalua.chunk;

import static org.junit.jupiter.api.Assertions.*;

import com.github.anilople.javalua.util.ByteUtils;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class EncodableTest {

  @Test
  void encodeCase1() {
    var person = new Person();
    person.b = 0x0;
    person.array = new byte[] {0x1, 0x2};
    assertArrayEquals(new byte[] {0x0, 0x1, 0x2}, person.encode());
  }

  static class Person implements Encodable {

    byte b;
    byte[] array;

    @Override
    public byte[] encode() {
      try {
        return ByteUtils.encode(this);
      } catch (IOException e) {
        throw new IllegalStateException("cannot happen", e);
      }
    }
  }
}