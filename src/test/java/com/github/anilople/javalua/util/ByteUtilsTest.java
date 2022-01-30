package com.github.anilople.javalua.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class ByteUtilsTest {

  @Test
  void encodeShortBigEndian() {
    assertArrayEquals(new byte[] {0x12, 0x34}, ByteUtils.encodeShortBigEndian((short) 0x1234));
  }

  @Test
  void encodeShortLittleEndian() {
    assertArrayEquals(new byte[] {0x34, 0x12}, ByteUtils.encodeShort((short) 0x1234, false));
  }

  /**
   * ch03 RETURN 指令无法读出，问题记录
   */
  @Test
  void decodeIntBigEndianCase0() {
    assertEquals(0x26008000, ByteUtils.decodeIntBigEndian(new byte[] {38, 0, -128, 0}));
  }

  @Test
  void decodeIntBigEndianCase1() {
    assertEquals(Integer.MIN_VALUE, ByteUtils.decodeIntBigEndian(new byte[] {-128, 0, 0, 0}));
  }

  @Test
  void decodeIntBigEndianCase3() {
    assertEquals(-1, ByteUtils.decodeIntBigEndian(new byte[] {-1, -1, -1, -1}));
  }

  @Test
  void decodeIntBigEndianCase4() {
    assertEquals(1 << 24, ByteUtils.decodeIntBigEndian(new byte[] {1, 0, 0, 0}));
  }

  @Test
  void decodeIntBigEndianCase6() {
    assertEquals(1, ByteUtils.decodeIntBigEndian(new byte[] {0, 0, 0, 1}));
  }

  @Test
  void decodeIntBigEndianCase7() {
    assertEquals(38 << 24, ByteUtils.decodeIntBigEndian(new byte[] {38, 0, 0, 0}));
  }

  @Test
  void decodeIntBigEndianCase8() {
    assertEquals((38 << 24) + 1, ByteUtils.decodeIntBigEndian(new byte[] {38, 0, 0, 1}));
  }

  @Test
  void decodeLongLowPartBigEndian() {
    long value =
        ByteUtils.decodeLongBigEndian(
            new byte[] {
              0x00, 0x00, 0x00, 0x00, 0x05, 0x06, 0x07, 0x08,
            });
    assertEquals(0x0000_0000_05060708L, value);
  }

  @Test
  void decodeLongLittleEndianLowestByte() {
    long value =
        ByteUtils.decodeLong(
            new byte[] {
              0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            },
            false);
    assertEquals(0x01, value);
  }

  @Test
  void encodeLongBigEndian() {
    assertArrayEquals(
        new byte[] {
          0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
        },
        ByteUtils.encodeLongBigEndian(0x0102030405060708L));
  }

  @Test
  void encodeLongLittleEndian() {
    assertArrayEquals(
        new byte[] {
          0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01,
        },
        ByteUtils.encodeLongBigEndian(0x0807060504030201L));
  }

  @Test
  void decodeLongBigEndian() {
    long value =
        ByteUtils.decodeLongBigEndian(
            new byte[] {
              0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
            });
    assertEquals(0x0102030405060708L, value);
  }

  @Test
  void low6BitsOf() {
    assertEquals(0, ByteUtils.low6BitsOf(0));
    assertEquals(0B111111, ByteUtils.low6BitsOf(-1));
    assertEquals(0, ByteUtils.low6BitsOf(0B111000000));
    assertEquals(0B101011, ByteUtils.low6BitsOf(0B11101011));
  }

  @Test
  void getBits() {
    assertEquals(0B11, ByteUtils.getBits(0B110, 1, 2));
    assertEquals(0B1, ByteUtils.getBits(0B110, 1, 1));
    assertEquals(0B11, ByteUtils.getBits(0B110, 1, 3));
    assertEquals(0B0, ByteUtils.getBits(0B110, 5, 6));

    assertEquals(0xFFFFFFF, ByteUtils.getBits(0xFFFFFFFF, 0, 28));
    assertEquals(0xFFFF, ByteUtils.getBits(0xFFFFFFFF, 0, 16));
    assertEquals(0xFFFF, ByteUtils.getBits(0xFFFFFFFF, 1, 16));
    assertEquals(0xFFFF, ByteUtils.getBits(0xFFFFFFFF, 16, 16));
  }
}
