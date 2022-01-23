package com.github.anilople.javalua.util;

import com.github.anilople.javalua.constant.SizeConstants;
import com.github.anilople.javalua.constant.SizeConstants.Java;
import java.util.Arrays;

/**
 * @author wxq
 */
public class ArrayUtils {

  public static byte[] concat(byte byte1, byte[] b) {
    final var newLength = 1 + b.length;
    var result = new byte[newLength];
    result[0] = byte1;
    System.arraycopy(b, 0, result, 1, b.length);
    return result;
  }

  public static byte[] concat(byte[] a, byte[] b) {
    final var newLength = a.length + b.length;
    var result = Arrays.copyOf(a, newLength);
    System.arraycopy(b, 0, result, a.length, b.length);
    return result;
  }

  public static byte[] concat(byte[] a, byte mid, byte[] b) {
    final var newLength = a.length + b.length + 1;
    var result = Arrays.copyOf(a, newLength);
    result[a.length] = mid;
    System.arraycopy(b, 0, result, a.length + 1, b.length);
    return result;
  }

  public static byte[] concat(byte first, byte[] a, byte[] b) {
    final var newLength = a.length + b.length + 1;
    var result = new byte[newLength];
    result[0] = first;
    System.arraycopy(a, 0, result, 1, a.length);
    System.arraycopy(b, 0, result, a.length + 1, b.length);
    return result;
  }

  public static byte[] toByteArray(int[] intArray) {
    byte[] byteArray = new byte[intArray.length * Java.INT];
    int startPosition = 0;
    for (int intValue : intArray) {
      ByteUtils.encodeInt(intValue, byteArray, startPosition);
      startPosition += Java.INT;
    }
    return byteArray;
  }

}
