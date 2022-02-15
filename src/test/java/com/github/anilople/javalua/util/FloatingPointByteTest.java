package com.github.anilople.javalua.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class FloatingPointByteTest {

  static void ensureSelfEquals(int eeeeexxx) {
    var valueDecoded = FloatingPointByte.decode(eeeeexxx);
    var valueEncoded = FloatingPointByte.encode(valueDecoded);
    assertEquals(eeeeexxx, valueEncoded);
  }

  @Test
  void selfLessThan8() {
    for (int i = 0; i <= 0b111; i++) {
      ensureSelfEquals(i);
    }
  }

  @Test
  void self() {
    ensureSelfEquals(0b1_111);
    ensureSelfEquals(0b10_111);
    ensureSelfEquals(0b11_111);
    ensureSelfEquals(0b111_111);
  }

  @Test
  void decode() {
    assertEquals(8, FloatingPointByte.decode(0b1_000));
    assertEquals(8 << 1, FloatingPointByte.decode(0b10_000));

    assertEquals(15, FloatingPointByte.decode(0b1_111));
    assertEquals(15 << 1, FloatingPointByte.decode(0b10_111));
  }
}