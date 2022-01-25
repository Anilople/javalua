package com.github.anilople.javalua.util;

import static com.github.anilople.javalua.util.ReflectionUtils.getFieldValue;
import static com.github.anilople.javalua.util.ReflectionUtils.getFieldValueArrayLength;
import static com.github.anilople.javalua.util.ReflectionUtils.getNonStaticDeclaredFields;
import static com.github.anilople.javalua.util.ReflectionUtils.isArray;
import static com.github.anilople.javalua.util.ReflectionUtils.isPrimitive;
import static com.github.anilople.javalua.util.ReflectionUtils.newInstance;
import static com.github.anilople.javalua.util.ReflectionUtils.setFieldValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用 大端 bigEndian 处理 byte 数组
 *
 * @author wxq
 */
public class ByteUtils {

  /**
   * big endian or little endian.
   * <p>
   * 在书里，作者的是 little endian
   */
  private static final boolean BIG_ENDIAN = false;

  /**
   * value为null时，对应的byte数组
   */
  private static final Map<Class<?>, byte[]> PRIMITIVE_TYPE_TO_EMPTY_BYTE_ARRAY;

  static {
    // see https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
    Map<Class<?>, byte[]> primitiveType2emptyByteArray = new HashMap<>();
    primitiveType2emptyByteArray.put(Byte.TYPE, new byte[1]);
    primitiveType2emptyByteArray.put(Short.TYPE, new byte[2]);
    primitiveType2emptyByteArray.put(Integer.TYPE, new byte[4]);
    primitiveType2emptyByteArray.put(Long.TYPE, new byte[8]);
    primitiveType2emptyByteArray.put(Float.TYPE, new byte[4]);
    primitiveType2emptyByteArray.put(Double.TYPE, new byte[8]);

    primitiveType2emptyByteArray.put(Character.TYPE, new byte[2]);

    PRIMITIVE_TYPE_TO_EMPTY_BYTE_ARRAY = Collections.unmodifiableMap(primitiveType2emptyByteArray);
  }

  static byte[] getEmptyByteArrayOf(Class<?> clazz) {
    if (clazz.isPrimitive()) {
      throw new IllegalArgumentException("type " + clazz + " isn't primitive type");
    }
    if (PRIMITIVE_TYPE_TO_EMPTY_BYTE_ARRAY.containsKey(clazz)) {
      throw new IllegalStateException("cannot resolve empty byte array of type " + clazz);
    }
    return PRIMITIVE_TYPE_TO_EMPTY_BYTE_ARRAY.get(clazz);
  }

  /**
   * 序列化java对象成byte数组
   */
  public static byte[] encode(Object object) throws IOException {
    Class<?> clazz = object.getClass();
    final List<Field> nonStaticDeclaredFields = getNonStaticDeclaredFields(clazz);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    for (Field declaredField : nonStaticDeclaredFields) {
      byte[] bytes = encode(object, declaredField);
      byteArrayOutputStream.write(bytes);
    }
    return byteArrayOutputStream.toByteArray();
  }

  static byte[] encode(Object object, Field field) {
    final Object value = getFieldValue(object, field);
    final Class<?> fieldType = field.getType();
    if (isPrimitive(fieldType)) {
      return encodePrimitive(fieldType, value);
    }
    if (isArray(fieldType)) {
      return encodeArray(fieldType, value);
    }

    throw new UnsupportedOperationException("field type " + fieldType + " in object " + object);
  }

  static byte[] encodePrimitive(Class<?> fieldType, Object value) {
    if (null == value) {
      return getEmptyByteArrayOf(fieldType);
    }
    if (Byte.TYPE.equals(fieldType)) {
      return new byte[] {(byte) value};
    }
    if (Integer.TYPE.equals(fieldType)) {
      return encodeInt((int) value, BIG_ENDIAN);
    }
    if (Long.TYPE.equals(fieldType)) {
      return encodeLong((long) value, BIG_ENDIAN);
    }
    if (Float.TYPE.equals(fieldType)) {
      return encodeFloat((float) value, BIG_ENDIAN);
    }
    if (Double.TYPE.equals(fieldType)) {
      return encodeDouble((double) value, BIG_ENDIAN);
    }
    throw new UnsupportedOperationException("primitive type " + fieldType + " value " + value);
  }

  public static byte[] encodeShort(short value) {
    return encodeShort(value, BIG_ENDIAN);
  }

  static byte[] encodeShort(short value, boolean bigEndian) {
    if (bigEndian) {
      return encodeShortBigEndian(value);
    } else {
      var newValue = Short.reverseBytes(value);
      return encodeShortBigEndian(newValue);
    }
  }

  static byte[] encodeShortBigEndian(short value) {
    byte lowPart = (byte) value;
    byte highPart = (byte) (value >> 8);
    return new byte[] {highPart, lowPart};
  }

  static void encodeInt(int value, byte[] bytes, int startPosition) {
    // TODO, performance
    byte[] byteArray = encodeInt(value);
    System.arraycopy(byteArray, 0, bytes, startPosition, byteArray.length);
  }

  public static byte[] encodeInt(int value) {
    return encodeInt(value, BIG_ENDIAN);
  }

  static byte[] encodeInt(int value, boolean bigEndian) {
    if (bigEndian) {
      return encodeIntBigEndian(value);
    } else {
      var newValue = Integer.reverseBytes(value);
      return encodeIntBigEndian(newValue);
    }
  }

  static byte[] encodeIntBigEndian(int value) {
    byte[] lowPart = encodeShortBigEndian((short) (value & 0xFFFF));
    byte[] highPart = encodeShortBigEndian((short) (value >> 16));
    return ArrayUtils.concat(highPart, lowPart);
  }

  public static byte[] encodeLong(long value) {
    return encodeLong(value, BIG_ENDIAN);
  }

  static byte[] encodeLong(long value, boolean bigEndian) {
    if (bigEndian) {
      return encodeLongBigEndian(value);
    } else {
      var newValue = Long.reverseBytes(value);
      return encodeLongBigEndian(newValue);
    }
  }

  static byte[] encodeLongBigEndian(long value) {
    byte[] lowPart = encodeIntBigEndian((int) (value));
    byte[] highPart = encodeIntBigEndian((int) (value >> 32));
    return ArrayUtils.concat(highPart, lowPart);
  }

  static byte[] encodeFloat(float value, boolean bigEndian) {
    int rawIntBits = Float.floatToRawIntBits(value);
    return encodeInt(rawIntBits, bigEndian);
  }

  public static byte[] encodeDouble(double value) {
    return encodeDouble(value, BIG_ENDIAN);
  }

  static byte[] encodeDouble(double value, boolean bigEndian) {
    long rawLongBits = Double.doubleToRawLongBits(value);
    return encodeLong(rawLongBits, bigEndian);
  }

  static byte[] encodeArray(Class<?> fieldType, Object value) {
    Class<?> componentType = fieldType.getComponentType();
    if (Byte.TYPE.equals(componentType)) {
      return (byte[]) value;
    }
    throw new UnsupportedOperationException("array type " + fieldType + " value " + value);
  }

  /**
   * 反序列化byte数组成java对象
   */
  public static <T> T decode(byte[] bytes, Class<T> clazz) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
    return decode(byteArrayInputStream, clazz);
  }

  static <T> T decode(InputStream inputStream, Class<T> clazz) throws IOException {
    if (isArray(clazz)) {
      throw new IllegalArgumentException("not allow array type");
    }
    if (isPrimitive(clazz)) {
      return (T) decodePrimitive(inputStream, clazz);
    }
    final T object = newInstance(clazz);
    final List<Field> nonStaticDeclaredFields = getNonStaticDeclaredFields(clazz);
    for (Field declaredField : nonStaticDeclaredFields) {
      Object fieldValue = decodeField(inputStream, object, declaredField);
      setFieldValue(object, declaredField, fieldValue);
    }
    return object;
  }

  static Object decodeField(InputStream inputStream, Object object, Field declaredField)
      throws IOException {
    Class<?> declaredFieldType = declaredField.getType();
    if (isPrimitive(declaredFieldType)) {
      return decodePrimitive(inputStream, declaredFieldType);
    }
    if (isArray(declaredFieldType)) {
      int arrayLength = getFieldValueArrayLength(object, declaredField);
      return decodeArray(inputStream, declaredFieldType, arrayLength);
    }

    return decode(inputStream, declaredFieldType);
  }

  static Object decodePrimitive(InputStream inputStream, Class<?> fieldType) throws IOException {
    if (Byte.TYPE.equals(fieldType)) {
      return (byte) inputStream.read();
    }
    if (Integer.TYPE.equals(fieldType)) {
      byte[] bytes = inputStream.readNBytes(4);
      return decodeInt(bytes, BIG_ENDIAN);
    }
    if (Long.TYPE.equals(fieldType)) {
      byte[] bytes = inputStream.readNBytes(8);
      return decodeLong(bytes, BIG_ENDIAN);
    }
    if (Float.TYPE.equals(fieldType)) {
      byte[] bytes = inputStream.readNBytes(4);
      return decodeFloat(bytes, BIG_ENDIAN);
    }
    if (Double.TYPE.equals(fieldType)) {
      byte[] bytes = inputStream.readNBytes(8);
      return decodeDouble(bytes, BIG_ENDIAN);
    }
    throw new UnsupportedOperationException("primitive type " + fieldType);
  }

  static short decodeShort(byte[] bytes, boolean bigEndian) {
    short value = decodeShortBigEndian(bytes);
    return bigEndian ? value : Short.reverseBytes(value);
  }

  static short decodeShortBigEndian(byte[] bytes) {
    byte highPart = bytes[0];
    byte lowPart = bytes[1];
    short value = highPart;
    value <<= 8;
    value |= lowPart;
    return value;
  }

  public static int decodeInt(byte[] bytes) {
    return decodeInt(bytes, BIG_ENDIAN);
  }

  static int decodeInt(byte[] bytes, boolean bigEndian) {
    int value = decodeIntBigEndian(bytes);
    return bigEndian ? value : Integer.reverseBytes(value);
  }

  static int decodeIntBigEndian(byte[] bytes) {
    short highPart = decodeShortBigEndian(Arrays.copyOfRange(bytes, 0, 2));
    short lowPart = decodeShortBigEndian(Arrays.copyOfRange(bytes, 2, 4));
    int value = highPart;
    value <<= 16;
    value |= lowPart;
    return value;
  }

  public static long decodeLong(byte[] bytes) {
    return decodeLong(bytes, BIG_ENDIAN);
  }

  static long decodeLong(byte[] bytes, boolean bigEndian) {
    long value = decodeLongBigEndian(bytes);
    return bigEndian ? value : Long.reverseBytes(value);
  }

  static long decodeLongBigEndian(byte[] bytes) {
    int highPart = decodeIntBigEndian(Arrays.copyOfRange(bytes, 0, 4));
    int lowPart = decodeIntBigEndian(Arrays.copyOfRange(bytes, 4, 8));
    long value = highPart;
    value <<= 32;
    value |= lowPart;
    return value;
  }

  static float decodeFloat(byte[] bytes, boolean bigEndian) {
    int rawIntBits = decodeInt(bytes, bigEndian);
    return Float.intBitsToFloat(rawIntBits);
  }

  public static double decodeDouble(byte[] bytes) {
    return decodeDouble(bytes, BIG_ENDIAN);
  }

  static double decodeDouble(byte[] bytes, boolean bigEndian) {
    long rawLongBits = decodeLong(bytes, bigEndian);
    return Double.longBitsToDouble(rawLongBits);
  }

  static Object decodeArray(InputStream inputStream, Class<?> fieldType, int arrayLength)
      throws IOException {
    Class<?> componentType = fieldType.getComponentType();
    if (!componentType.isPrimitive()) {
      throw new UnsupportedOperationException("only support primitive type now");
    }
    if (Byte.TYPE.equals(componentType)) {
      byte[] bytes = new byte[arrayLength];
      for (int i = 0; i < arrayLength; i++) {
        byte value = decode(inputStream, Byte.TYPE);
        bytes[i] = value;
      }
      return bytes;
    } else {
      throw new UnsupportedOperationException("array type " + fieldType);
    }
  }

  static String toHexString(byte b) {
    return String.format("%02x", b).toUpperCase();
  }

  /**
   * @return hexadecimal string of bytes
   */
  public static String toHexString(byte[] bytes) {
    List<String> hexStrings = new ArrayList<>();
    for (byte b : bytes) {
      String hexString = toHexString(b);
      hexStrings.add(hexString);
    }
    return String.join(" ", hexStrings);
  }

  static class EncodeRuntimeException extends RuntimeException {

    public EncodeRuntimeException(String message, Throwable e) {
      super(message, e);
    }
  }

  static class DecodeRuntimeException extends RuntimeException {

    public DecodeRuntimeException(String message, Throwable e) {
      super(message, e);
    }
  }
}
