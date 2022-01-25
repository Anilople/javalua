package com.github.anilople.javalua.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author wxq
 */
class ArrayUtilsTest {

  @Test
  void toByteArray() {
    byte[] byteArray = ArrayUtils.toByteArray(new int[] {1, 2});
    assertArrayEquals(
        new byte[] {
          1, 0, 0, 0, 2, 0, 0, 0,
        },
        byteArray);
  }

  @Test
  void toArray() {
    byte[] byteArray = ArrayUtils.toByteArray(new int[] {1, 2, 3});
    int[] intArray = ArrayUtils.toIntArray(byteArray);

    assertArrayEquals(byteArray, ArrayUtils.toByteArray(intArray));
    assertArrayEquals(intArray, ArrayUtils.toIntArray(byteArray));
  }
}
