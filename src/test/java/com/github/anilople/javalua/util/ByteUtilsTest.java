package com.github.anilople.javalua.util;

import static org.junit.jupiter.api.Assertions.*;

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
}
