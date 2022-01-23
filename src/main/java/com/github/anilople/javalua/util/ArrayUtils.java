package com.github.anilople.javalua.util;

import java.util.Arrays;

/**
 * @author wxq
 */
public class ArrayUtils {

  public static byte[] concatByteArray(byte[] a, byte[] b) {
    final int newLength = a.length + b.length;
    byte[] result = Arrays.copyOf(a, newLength);
    System.arraycopy(b, 0, result, a.length, b.length);
    return result;
  }

}
